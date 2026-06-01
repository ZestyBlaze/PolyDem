package dev.zestyblaze.demeter.block;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.mixin.PropertiesAccessor;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public abstract class SimplePolymerBiomePlantBlock extends Block implements PolymerBlock {
    public SimplePolymerBiomePlantBlock(Properties settings) {
        super(settings);
    }

    public static SimplePolymerBiomePlantBlock create(Properties settings) {
        if (PolymerBlockResourceUtils.getBlocksLeft(BlockModelType.BIOME_PLANT) > 0
                && Demeter.config.miscConfig.useFullTexturedBlocks.get()) {
            return new TexturedBlock(settings);
        }

        return new VirtualBlock(settings);
    }

    @Override
    protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        return level.getBlockState(pos.below()).is(BlockTags.SUPPORTS_VEGETATION);
    }

    @Override
    protected boolean propagatesSkylightDown(final BlockState state) {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected boolean isPathfindable(final BlockState state, final PathComputationType type) {
        return type == PathComputationType.AIR && !this.hasCollision || super.isPathfindable(state, type);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    private static class TexturedBlock extends SimplePolymerBiomePlantBlock implements PolymerTexturedBlock {
        private final BlockState state;

        public TexturedBlock(Properties settings) {
            super(settings);
            this.state = PolymerBlockResourceUtils.requestBlock(BlockModelType.BIOME_PLANT, PolymerBlockModel.of(
                    ((PropertiesAccessor) settings).getId().identifier().withPrefix("block/")));
        }

        @Override
        public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
            return this.state;
        }
    }

    private static class VirtualBlock extends SimplePolymerBiomePlantBlock implements FactoryBlock, PolymerTexturedBlock {
        private final BlockState state;

        public VirtualBlock(Properties settings) {
            super(settings);
            this.state = PolymerBlockResourceUtils.requestEmpty(BlockModelType.BIOME_PLANT);
        }

        @Override
        public BlockState getPolymerBlockState(BlockState blockState, PacketContext packetContext) {
            return this.state;
        }

        @Override
        public @Nullable ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
            var model = new BlockModel();
            var element = ItemDisplayElementUtil.createSimple(this.asItem());
            var ele2 = ItemDisplayElementUtil.createSimple(this.asItem());
            element.setRotation(0, 45);
            element.setScale(new Vector3f(1.4f, 1, 0.01f));
            ele2.setRotation(0, 135);
            ele2.setScale(new Vector3f(1.4f, 1, 0.01f));
            model.addElement(element);
            model.addElement(ele2);
            return model;
        }
    }
}
