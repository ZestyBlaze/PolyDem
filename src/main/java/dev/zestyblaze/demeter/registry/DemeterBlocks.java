package dev.zestyblaze.demeter.registry;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.block.PolymerRotatedPillarBlock;
import dev.zestyblaze.demeter.block.other.SimpleFastBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class DemeterBlocks {
    public static final Block MAPLE_LOG = register("maple_log", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), PolymerRotatedPillarBlock::create);
    public static final Block STRIPPED_MAPLE_LOG = register("stripped_maple_log", BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG), PolymerRotatedPillarBlock::create);
    public static final Block MAPLE_PLANKS = register("maple_planks", BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), SimpleFastBlock::create);

    public static void init() {
    }

    public static <T extends Block> T register(String path, BlockBehaviour.Properties settings, Function<BlockBehaviour.Properties, T> function) {
        Identifier id = Demeter.createId(path);
        T item = function.apply(settings.setId(ResourceKey.create(Registries.BLOCK, id)));
        return Registry.register(BuiltInRegistries.BLOCK, id, item);
    }
}
