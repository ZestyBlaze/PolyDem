package dev.zestyblaze.demeter.block;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.mixin.PropertiesAccessor;
import dev.zestyblaze.demeter.registry.DemeterBlocks;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.block.model.generic.BSMMParticleBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;

public class PolymerRotatedPillarBlock extends RotatedPillarBlock implements FactoryBlock, PolymerTexturedBlock {
    private final EnumMap<Direction.Axis, BlockState> AXIS_STATE_MAP = new EnumMap<>(Direction.Axis.class);
    private boolean useFullBlock = true;

    public PolymerRotatedPillarBlock(Properties properties) {
        super(properties);

        AXIS_STATE_MAP.put(Direction.Axis.X, PolymerBlockResourceUtils.requestBlock(
                BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                        ((PropertiesAccessor)properties).getId().identifier().withPrefix("block/"),
                        90, 90)
        ));
        AXIS_STATE_MAP.put(Direction.Axis.Y, PolymerBlockResourceUtils.requestBlock(
                BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                        ((PropertiesAccessor)properties).getId().identifier().withPrefix("block/")
                )
        ));
        AXIS_STATE_MAP.put(Direction.Axis.Z, PolymerBlockResourceUtils.requestBlock(
                BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                        ((PropertiesAccessor)properties).getId().identifier().withPrefix("block/"),
                        90, 0)
        ));

        if (PolymerBlockResourceUtils.getBlocksLeft(BlockModelType.FULL_BLOCK) < 0
                || !Demeter.config.miscConfig.useFullTexturedBlocks.get()) {
            useFullBlock = false;
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (itemStack.is(ItemTags.AXES)) {
                if (state.is(DemeterBlocks.MAPLE_LOG)) {
                    level.setBlockAndUpdate(pos, DemeterBlocks.STRIPPED_MAPLE_LOG.defaultBlockState().setValue(AXIS, state.getValue(AXIS)));
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, @Nullable PacketContext context) {
        return useFullBlock ? AXIS_STATE_MAP.get(state.getValue(AXIS)) : Blocks.BARRIER.defaultBlockState();
    }

    @Override
    public ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        var model = new BlockModel();
        BlockState state = world.getBlockState(pos);
        if (!useFullBlock) {
            var element = ItemDisplayElementUtil.createSimple(this.asItem());
            element.setScale(new Vector3f(2));
            switch (state.getValue(AXIS)) {
                case X -> element.setRotation(90, 90);
                case Y -> element.setRotation(0, 0);
                case Z -> element.setRotation(90, 0);
            }
            model.addElement(element);
        }
        return model;
    }
}
