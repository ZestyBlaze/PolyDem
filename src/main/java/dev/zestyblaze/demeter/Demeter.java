package dev.zestyblaze.demeter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.zestyblaze.demeter.config.DemeterConfig;
import dev.zestyblaze.demeter.event.BlockEvents;
import dev.zestyblaze.demeter.event.LevelEvents;
import dev.zestyblaze.demeter.managers.DemeterCropStatsManager;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.registry.DemeterComponents;
import dev.zestyblaze.demeter.registry.DemeterEnchantments;
import dev.zestyblaze.demeter.util.NewDayCallback;
import dev.zestyblaze.demeter.util.QualityUtil;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.v1.DataResourceLoader;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.mixin.item.ItemStackMixin;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class Demeter implements ModInitializer {
	public static final String MOD_ID = "demeter";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static DemeterConfig config = ConfigApiJava.registerAndLoadConfig(DemeterConfig::new);

	@Override
	public void onInitialize() {
		//Inits
		DemeterAttachments.init();
		DemeterComponents.init();

		//Event Callbacks
		BlockEvents.init();
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


			if (holder.is(key -> key.identifier().getPath().contains("shearing/sheep"))) {
				HolderLookup.Provider registryAccess = context.getLevel().registryAccess();

				BonusLevelTableCondition condition = new BonusLevelTableCondition(
						registryAccess.getOrThrow(DemeterEnchantments.BARBER), List.of(0.0f, 1.0f));

				if (condition.test(context)) {
                    drops.addAll(List.copyOf(drops));
				}
			}
		});

		ItemComponentTooltipProviderRegistry.addFirst(DemeterComponents.QUALITY);
		DataResourceLoader.get().registerReloadListener(createId("crop_data"), DemeterCropStatsManager::new);
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
}