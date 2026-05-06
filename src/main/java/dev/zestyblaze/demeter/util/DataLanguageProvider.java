package dev.zestyblaze.demeter.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.stats.StatType;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public abstract class DataLanguageProvider implements DataProvider {
    protected final FabricPackOutput dataOutput;
    private final String languageCode;
    private final CompletableFuture<HolderLookup.Provider> registryLookup;

    public DataLanguageProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        this(dataOutput, "en_us", registryLookup);
    }

    public DataLanguageProvider(FabricPackOutput dataOutput, String languageCode, CompletableFuture<HolderLookup.Provider> registryLookup) {
        this.dataOutput = dataOutput;
        this.languageCode = languageCode;
        this.registryLookup = registryLookup;
    }

    public abstract void generateTranslations(HolderLookup.Provider var1, TranslationBuilder var2);

    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        TreeMap<String, String> translationEntries = new TreeMap<>();
        return this.registryLookup.thenCompose((lookup) -> {
            this.generateTranslations(lookup, (key, value) -> {
                Objects.requireNonNull(key);
                Objects.requireNonNull(value);
                if (translationEntries.containsKey(key)) {
                    throw new RuntimeException("Existing translation key found - " + key + " - Duplicate will be ignored.");
                } else {
                    translationEntries.put(key, value);
                }
            });
            JsonObject langEntryJson = new JsonObject();

            for (Map.Entry<String, String> stringStringEntry : translationEntries.entrySet()) {
                langEntryJson.addProperty(stringStringEntry.getKey(), stringStringEntry.getValue());
            }

            return DataProvider.saveStable(writer, langEntryJson, this.getLangFilePath(this.languageCode));
        });
    }

    private Path getLangFilePath(String code) {
        return this.dataOutput.createPathProvider(PackOutput.Target.DATA_PACK, "lang").json(Identifier.fromNamespaceAndPath(this.dataOutput.getModId(), code));
    }

    public String getName() {
        return "Language (%s)".formatted(this.languageCode);
    }

    @FunctionalInterface
    @ApiStatus.NonExtendable
    public interface TranslationBuilder {
        void add(String var1, String var2);

        default void add(Item item, String value) {
            this.add(item.getDescriptionId(), value);
        }

        default void add(Block block, String value) {
            this.add(block.getDescriptionId(), value);
        }

        default void add(ResourceKey<CreativeModeTab> registryKey, String value) {
            Holder<CreativeModeTab> group = BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(registryKey);
            ComponentContents content = group.value().getDisplayName().getContents();
            if (content instanceof TranslatableContents translatableTextContent) {
                this.add(translatableTextContent.getKey(), value);
            } else {
                throw new UnsupportedOperationException("Cannot add language entry for ItemGroup (%s) as the display name is not translatable.".formatted(group.value().getDisplayName().getString()));
            }
        }

        default void add(EntityType<?> entityType, String value) {
            this.add(entityType.getDescriptionId(), value);
        }

        default void addEnchantment(ResourceKey<Enchantment> enchantment, String value) {
            this.add(Util.makeDescriptionId("enchantment", enchantment.identifier()), value);
        }

        default void add(Holder<Attribute> entityAttribute, String value) {
            this.add(entityAttribute.value().getDescriptionId(), value);
        }

        default void add(StatType<?> statType, String value) {
            this.add("stat_type." + BuiltInRegistries.STAT_TYPE.getKey(statType).toString().replace(':', '.'), value);
        }

        default void add(MobEffect statusEffect, String value) {
            this.add(statusEffect.getDescriptionId(), value);
        }

        default void add(Identifier identifier, String value) {
            this.add(identifier.toLanguageKey(), value);
        }

        default void add(TagKey<?> tagKey, String value) {
            this.add(tagKey.getTranslationKey(), value);
        }

        default void add(Path existingLanguageFile) throws IOException {
            Reader reader = Files.newBufferedReader(existingLanguageFile);

            try {
                JsonObject translations = JsonParser.parseReader(reader).getAsJsonObject();

                for (String key : translations.keySet()) {
                    this.add(key, translations.get(key).getAsString());
                }
            } catch (Throwable var7) {
                try {
                    reader.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            reader.close();

        }
    }
}

