package dev.zestyblaze.demeter.managers;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.data.CropItemSeason;
import dev.zestyblaze.demeter.world.season.Season;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.CropBlock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemeterCropSeasonManager extends SimpleJsonResourceReloadListener<CropItemSeason> {
    private static final Map<Item, CropItemSeason> newMap = new HashMap<>();

    public DemeterCropSeasonManager() {
        super(CropItemSeason.CODEC, FileToIdConverter.registry(ResourceKey.createRegistryKey(Demeter.createId("crop_seasons"))));
    }

    public static boolean itemHasSeasons(Item item) {
        return newMap.containsKey(item);
    }

    public static List<Season> getValidSeasons(Item item) {
        return newMap.get(item).validSeasons();
    }

    @Override
    protected void apply(Map<Identifier, CropItemSeason> preparations, ResourceManager manager, ProfilerFiller profiler) {
        newMap.clear();
        for (Map.Entry<Identifier, CropItemSeason> entry : preparations.entrySet()) {
            if (BuiltInRegistries.ITEM.containsKey(entry.getKey())) {
                Item item = BuiltInRegistries.ITEM.getValue(entry.getKey());

                if (item instanceof BlockItem blockItem && blockItem.getBlock() instanceof CropBlock) {
                    newMap.put(BuiltInRegistries.ITEM.getValue(entry.getKey()), entry.getValue());
                }
            }
        }
    }
}
