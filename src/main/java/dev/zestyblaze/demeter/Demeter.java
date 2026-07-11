package dev.zestyblaze.demeter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.zestyblaze.demeter.config.DemeterConfig;
import dev.zestyblaze.demeter.datagen.providers.DemeterWorldGenProvider;
import dev.zestyblaze.demeter.event.BlockEvents;
import dev.zestyblaze.demeter.event.EntityEvents;
import dev.zestyblaze.demeter.event.LevelEvents;
import dev.zestyblaze.demeter.managers.DemeterAnimalNamesManager;
import dev.zestyblaze.demeter.managers.DemeterAnimalStatsManager;
import dev.zestyblaze.demeter.managers.DemeterCropStatsManager;
import dev.zestyblaze.demeter.registry.*;
import dev.zestyblaze.demeter.tags.DemeterBiomeTags;
import dev.zestyblaze.demeter.util.NewDayCallback;
import dev.zestyblaze.demeter.util.QualityUtil;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.v1.DataResourceLoader;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantable;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class Demeter implements ModInitializer {
	public static final String MOD_ID = "demeter";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static DemeterConfig config = ConfigApiJava.registerAndLoadConfig(DemeterConfig::new);

	@Override
	public void onInitialize() {
		//Inits
		DemeterAdvancementCriterion.init();
		DemeterAttachments.init();
		DemeterBlockEntities.init();
		DemeterBlocks.init();
		DemeterBlockStateProviders.init();
		DemeterComponents.init();
		DemeterConsumables.init();
		DemeterCreativeModeTabs.init();
		DemeterItems.init();
		DemeterRecipeSerializers.init();
		DemeterRecipeTypes.init();

		//Event Callbacks
		BlockEvents.init();
		EntityEvents.init();
		LevelEvents.init();

		//Run Custom Events
		ServerTickEvents.END_LEVEL_TICK.register(serverLevel -> {
			if (serverLevel.getOverworldClockTime() % 24000L == 1) {
				NewDayCallback.EVENT.invoker().onNewDay(serverLevel);
			}
		});

		LootTableEvents.MODIFY_DROPS.register((holder, context, drops) -> {
			if (context.hasParameter(LootContextParams.THIS_ENTITY)) {
				drops.forEach(QualityUtil::randomiseQuality);
			}

			if (holder.is(BuiltInLootTables.SHEAR_SHEEP)) {
				HolderLookup.Provider registryAccess = context.getLevel().registryAccess();

				BonusLevelTableCondition condition = new BonusLevelTableCondition(
						registryAccess.getOrThrow(DemeterEnchantments.BARBER), List.of(0.0f, 1.0f));

				if (condition.test(context)) {
                    drops.addAll(List.copyOf(drops));
				}
			}
		});

		ItemComponentTooltipProviderRegistry.addFirst(DemeterComponents.QUALITY);

		DataResourceLoader.get().registerReloadListener(createId("animal_data"), DemeterAnimalStatsManager::new);
		DataResourceLoader.get().registerReloadListener(createId("animal_names"), new DemeterAnimalNamesManager());
		DataResourceLoader.get().registerReloadListener(createId("crop_data"), DemeterCropStatsManager::new);

		DefaultItemComponentEvents.MODIFY.register(context -> {
			context.modify(Items.SHEARS, builder -> builder.set(DataComponents.ENCHANTABLE, new Enchantable(10)));
		});

		BiomeModifications.addFeature(
				doubleTag(ConventionalBiomeTags.IS_JUNGLE, ConventionalBiomeTags.IS_HOT),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				DemeterWorldGenProvider.BAMBOO_SHOOTS_PATCH_PF
		);
		BiomeModifications.addFeature(
				herbPred(),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				DemeterWorldGenProvider.HERB_PATCH_PF
		);
		BiomeModifications.addFeature(
				BiomeSelectors.tag(DemeterBiomeTags.HAS_MAPLE_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				DemeterWorldGenProvider.MAPLE_TREES_PF
		);

		PolymerResourcePackUtils.addModAssets(MOD_ID);
	}

	public static Identifier createId(String id) {
		return Identifier.fromNamespaceAndPath(MOD_ID, id);
	}

	public static <E extends Enum<E>> Codec<E> enumCodec(Class<E> clazz) {
		return stringResolverCodec(e -> e.name().toLowerCase(Locale.ROOT), name -> Enum.valueOf(clazz, name.toUpperCase(Locale.ROOT)));
	}

	public static <E> Codec<E> stringResolverCodec(Function<E, String> p_184406_, Function<String, E> p_184407_) {
		return Codec.STRING.flatXmap((p_184404_) -> Optional.ofNullable(p_184407_.apply(p_184404_)).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Unknown element name:" + p_184404_)), (p_184401_) -> Optional.ofNullable(p_184406_.apply(p_184401_)).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Element with unknown name: " + p_184401_)));
	}

	public static Predicate<BiomeSelectionContext> doubleTag(TagKey<Biome> tag1, TagKey<Biome> tag2) {
		return context -> context.hasTag(tag1) && context.hasTag(tag2);
	}

	public static Predicate<BiomeSelectionContext> herbPred() {
		return context -> !context.hasTag(ConventionalBiomeTags.IS_AQUATIC) &&
				!context.hasTag(ConventionalBiomeTags.IS_HOT)
				&& !context.hasTag(ConventionalBiomeTags.IS_COLD);
	}
}