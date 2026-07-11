package dev.zestyblaze.demeter.world;

import com.mojang.serialization.MapCodec;
import dev.zestyblaze.demeter.registry.DemeterBlockStateProviders;
import dev.zestyblaze.demeter.registry.DemeterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

import java.util.List;

public class HerbBlockStateProvider extends BlockStateProvider {
    public static final HerbBlockStateProvider INSTANCE = new HerbBlockStateProvider();
    public static final MapCodec<HerbBlockStateProvider> CODEC = MapCodec.unit(INSTANCE);
    private static final List<Block> HERBS = List.of(DemeterBlocks.MINT, DemeterBlocks.CHAMOMILE, DemeterBlocks.LAVENDER);

    @Override
    protected BlockStateProviderType<?> type() {
        return DemeterBlockStateProviders.HERBS;
    }

    @Override
    public BlockState getState(WorldGenLevel level, RandomSource random, BlockPos pos) {
        return Util.getRandom(HERBS, level.getRandom()).defaultBlockState();
    }
}
