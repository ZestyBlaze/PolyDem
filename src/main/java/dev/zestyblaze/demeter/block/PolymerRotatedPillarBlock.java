package dev.zestyblaze.demeter.block;

import dev.zestyblaze.demeter.block.other.SimpleFastBlock;
import dev.zestyblaze.demeter.mixin.PropertiesAccessor;
import dev.zestyblaze.demeter.registry.DemeterBlocks;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;

public class PolymerRotatedPillarBlock extends SimpleFastBlock implements PolymerTexturedBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private final EnumMap<Direction.Axis, BlockState> AXIS_STATE_MAP = new EnumMap<>(Direction.Axis.class);

    public static SimpleFastBlock create(Properties settings) {
        if (PolymerBlockResourceUtils.getBlocksLeft(BlockModelType.FULL_BLOCK) > 0
            //&& PolyFactoryConfig.get().useFastFullBlocks
        ) {
            return new TexturedBlock(settings);
        }

        return new VirtualBlock(settings);
    }

    public PolymerRotatedPillarBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.Y));

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
        return AXIS_STATE_MAP.get(state.getValue(AXIS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis());
    }
}
