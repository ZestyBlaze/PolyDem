package dev.zestyblaze.demeter.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Reader;
import java.util.*;
import java.util.function.Function;

/**
 * A utility class lifted from Looot by Commoble
 * <a href="https://github.com/Commoble/looot/blob/main/src/main/java/commoble/looot/data/MergeableCodecDataManager.java">...</a>
 * Generic data loader for Codec-parsable data.
 * This works best if initialized during your mod's construction.
 * @param <RAW> The type of the objects that the codec is parsing JSONs as
 * @param <FINE> The type of the object we get after merging the parsed objects. Can be the same as RAW
 */
public class MergeableCodecDataManager<RAW, FINE> extends SimplePreparableReloadListener<Map<Identifier, FINE>> {
    private static final Logger LOGGER = LogManager.getLogger();

    /** ".json" **/
    protected static final String JSON_EXTENSION = ".json";
    /** 5 **/
    protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

    /** the loaded data **/
    protected Map<Identifier, FINE> data = new HashMap<>();

    private final String folderName;
    private final Codec<RAW> codec;
    private final Function<List<RAW>, FINE> merger;

    /**
     * Initialize a data manager with the given folder name, codec, and merger
     * @param folderName The name of the folder to load data from,
     * e.g. "cheeses" would load data from "data/modid/cheeses" for all modids.
     * Can include subfolders, e.g. "cheeses/sharp"
     * @param codec A codec that will be used to parse jsons. See drullkus's codec primer for help on creating these:
     * <a href="https://gist.github.com/Drullkus/1bca3f2d7f048b1fe03be97c28f87910">...</a>
     * @param merger A merging function that uses a list of java-objects-that-were-parsed-from-json to create a final object.
     * The list contains all successfully-parsed objects with the same ID from all mods and datapacks.
     * (for a json located at "data/modid/folderName/name.json", the object's ID is "modid:name")
     * As an example, consider vanilla's Tags: mods or datapacks can define tags with the same modid:name id,
     * and then all tag jsons defined with the same ID are merged additively into a single set of items, etc
     */
    public MergeableCodecDataManager(final String folderName, Codec<RAW> codec, final Function<List<RAW>, FINE> merger) {
        this.folderName = folderName;
        this.codec = codec;
        this.merger = merger;
    }

    public String folderName() {
        return this.folderName;
    }

    /**
     * @return The immutable map of data entries
     */
    public Map<Identifier, FINE> getData() {
        return this.data;
    }

    /** Off-thread processing (can include reading files from hard drive) **/
    @Override
    protected Map<Identifier, FINE> prepare(final ResourceManager resourceManager, final ProfilerFiller profiler) {
        final Map<Identifier, FINE> map = new HashMap<>();

        Map<Identifier, List<Resource>> resourceStacks = resourceManager.listResourceStacks(this.folderName, id -> id.getPath().endsWith(JSON_EXTENSION));
        for (var entry : resourceStacks.entrySet()) {
            List<RAW> raws = new ArrayList<>();
            Identifier fullId = entry.getKey();
            String fullPath = fullId.getPath(); // includes folderName/ and .json
            Identifier id = Identifier.fromNamespaceAndPath(
                    fullId.getNamespace(),
                    fullPath.substring(this.folderName.length() + 1, fullPath.length() - JSON_EXTENSION_LENGTH));

            for (Resource resource : entry.getValue()) {
                try(Reader reader = resource.openAsReader()) {
                    JsonElement jsonElement = JsonParser.parseReader(reader);
                    this.codec.parse(JsonOps.INSTANCE, jsonElement)
                            .resultOrPartial(errorMsg -> LOGGER.error("Error deserializing json {} in folder {} from pack {}: {}", id, this.folderName, resource.sourcePackId(), errorMsg))
                            .ifPresent(raws::add);
                }
                catch(Exception e) {
                    LOGGER.error(String.format(Locale.ENGLISH, "Error reading resource %s in folder %s from pack %s: ", id, this.folderName, resource.sourcePackId()), e);
                }
            }
            map.put(id, merger.apply(raws));
        }

        LOGGER.info("Data loader for {} loaded {} finalized objects", this.folderName, this.data.size());
        return Map.copyOf(map);
    }

    /** Main-thread processing, runs after prepare concludes **/
    @Override
    protected void apply(final Map<Identifier, FINE> processedData, final ResourceManager resourceManager, final ProfilerFiller profiler)
    {
        // now that we're on the main thread, we can finalize the data
        this.data = processedData;
    }
}
