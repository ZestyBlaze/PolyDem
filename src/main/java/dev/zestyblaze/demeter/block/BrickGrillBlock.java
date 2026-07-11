package dev.zestyblaze.demeter.block;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.block.entity.BrickGrillBlockEntity;
import dev.zestyblaze.demeter.registry.DemeterBlockEntities;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.tags.DemeterItemTags;
import eu.pb4.factorytools.api.block.FactoryBlock;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;

public class BrickGrillBlock extends BaseEntityBlock implements FactoryBlock, PolymerTexturedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final EnumMap<Direction, Pair<BlockState, BlockState>> stateMap = new EnumMap<>(Direction.class);
    private boolean useFullBlock = true;

    public BrickGrillBlock(Properties settings) {
        super(settings.noOcclusion());
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, false));

        stateMap.put(Direction.UP, Pair.of(
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill"))),
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill_lit")))
                )
        );
        stateMap.put(Direction.DOWN, Pair.of(
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill"))),
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill_lit")))
                )
        );

        stateMap.put(Direction.NORTH, Pair.of(
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill"))),
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill_lit")))
                )
        );
        stateMap.put(Direction.SOUTH, Pair.of(
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill"), 0, 180)),
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill_lit"), 0, 180))
                )
        );
        stateMap.put(Direction.WEST, Pair.of(
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill"), 0, 270)),
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill_lit"), 0, 270))
                )
        );
        stateMap.put(Direction.EAST, Pair.of(
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill"), 0, 90)),
                        PolymerBlockResourceUtils.requestBlock(BlockModelType.FULL_BLOCK, PolymerBlockModel.of(
                                Demeter.createId("block/brick_grill_lit"), 0, 90))
                )
        );

        if (PolymerBlockResourceUtils.getBlocksLeft(BlockModelType.FULL_BLOCK) < 0
                || !Demeter.config.miscConfig.useFullTexturedBlocks.get()) {
            useFullBlock = false;
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hitResult) {
        if (level instanceof ServerLevel serverLevel && !itemStack.isEmpty()) {
            if (serverLevel.getBlockEntity(pos) instanceof BrickGrillBlockEntity grillEntity) {
                Ingredient requiredDish = grillEntity.getRequiredDish();
                if (requiredDish != null) {
                    if (requiredDish.test(itemStack)) {
                        player.addItem(grillEntity.output);
                        grillEntity.setItem(0, ItemStack.EMPTY, true);
                        grillEntity.setRequiredDish(null);
                        return InteractionResult.SUCCESS_SERVER;
                    }
                }

                if (itemStack.is(DemeterItemTags.GRILL_STARTERS)) {
                    level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(LIT, !level.getBlockState(pos).getValue(LIT)));
                    return InteractionResult.SUCCESS_SERVER;
                } else if (grillEntity.output == ItemStack.EMPTY) {
                    for (int i = 0; i <= grillEntity.items.size(); i++) {
                        if (grillEntity.getItem(i).isEmpty()) {
                            grillEntity.setItem(i, itemStack.copyWithCount(1), false);
                            if (!player.isCreative()) itemStack.shrink(1);
                            grillEntity.setChanged();
                            return InteractionResult.SUCCESS_SERVER;
                        }
                    }
                }
            }
        } else if (itemStack.isEmpty()) {
            return useWithoutItem(state, level, pos, player, hitResult);
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof BrickGrillBlockEntity grillEntity) {
                if (player.isCrouching()) {
                    
                    for (int i = grillEntity.items.size() - 1; i >= 0; i--) {
                        ItemStack stack = grillEntity.getItem(i);
                        if (!stack.isEmpty()) {
                            grillEntity.setItem(i, ItemStack.EMPTY, false);
                            if (!player.addItem(stack)) {
                                player.drop(stack, true);
                            }
                            grillEntity.setChanged();
                            return InteractionResult.SUCCESS_SERVER;
                        }
                    }

                    return InteractionResult.SUCCESS_SERVER;
                } else {
                    if (grillEntity.output != ItemStack.EMPTY && grillEntity.getRequiredDish() == null) {
                        player.addItem(grillEntity.output);
                        grillEntity.setItem(0, ItemStack.EMPTY, true);
                        return InteractionResult.SUCCESS_SERVER;
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, @Nullable PacketContext context) {
        Pair<BlockState, BlockState> statePair = stateMap.get(state.getValue(FACING));
        if (useFullBlock) {
            if (state.getValue(LIT)) {
                return statePair.getSecond();
            } else {
                return statePair.getFirst();
            }
        } else {
            return Blocks.BARRIER.defaultBlockState();
        }
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new BrickGrillBlockEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, DemeterBlockEntities.BRICK_GRILL, BrickGrillBlockEntity::tick);
    }

    @Override
    public ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return new Model();
    }

    public static class Model extends BlockModel implements BrickGrillBlockEntity.ItemSetter {
        private final ItemDisplayElement main;

        private final ItemDisplayElement[] items = new ItemDisplayElement[4];

        private final ItemDisplayElement result;

        public Model() {
            this.main = ItemDisplayElementUtil.createSimple();
            this.main.setBillboardMode(Display.BillboardConstraints.VERTICAL);
            this.main.setScale(new Vector3f(1, 1, 0.01f));
            this.main.setOffset(new Vec3(0, 1, 0));
            this.main.setViewRange(0.03f);

            for (int i = 0; i < 4; i++) {
                this.items[i] = ItemDisplayElementUtil.createSimple();
                this.items[i].setItemDisplayContext(ItemDisplayContext.GUI);
                this.items[i].setBillboardMode(Display.BillboardConstraints.VERTICAL);
                this.items[i].setScale(new Vector3f(0.3f, 0.3f, 0.01f));
                this.items[i].setOffset(new Vec3(0, 1f, 0));
                this.items[i].setTranslation(
                        new Vector3f(
                                (i % 2 == 0 ? -0.2f : 0.2f),
                                (i < 2 ? 0.25f : -0.15f),
                                0.01f)
                );
                this.items[i].setLeftRotation(new Quaternionf().rotateY((float) Math.PI));
                this.items[i].setViewRange(0.03f);
                this.addElement(items[i]);
            }

            this.result = ItemDisplayElementUtil.createSimple();
            this.result.setItemDisplayContext(ItemDisplayContext.GUI);
            this.result.setBillboardMode(Display.BillboardConstraints.VERTICAL);
            this.result.setScale(new Vector3f(0.5f, 0.5f, 0.01f));
            this.result.setOffset(new Vec3(0, 1f, 0));
            this.result.setTranslation(new Vector3f(0, 0.08f, 0.01f));
            this.result.setLeftRotation(new Quaternionf().rotateY((float) Math.PI));
            this.result.setViewRange(0.03f);
        }

        @Override
        public void setItem(int slot, ItemStack stack, boolean isResultItem) {
            ItemStack model;
            boolean hasItems = false;
            boolean hasResult = false;

            if (stack.isEmpty()) {
                model = ItemStack.EMPTY;
            } else {
                model = stack.copy();
            }

            if (isResultItem) {
                this.result.setItem(model);
            } else {
                this.items[slot].setItem(model);
            }

            if (model.isEmpty()) {
                if (isResultItem) {
                    this.removeElement(this.result);
                } else {
                    this.removeElement(this.items[slot]);
                }
            } else {
                if (isResultItem) {
                    this.addElement(this.result);
                } else {
                    this.addElement(this.items[slot]);
                }
            }

            for (ItemDisplayElement item : items) {
                if (!item.getItem().isEmpty()) {
                    hasItems = true;
                    break;
                }
            }

            if (!this.result.getItem().isEmpty()) {
                hasResult = true;
            }

            if (hasItems || hasResult) {
                this.main.setItem(new ItemStack(DemeterItems.TEXT_BOX));
                this.addElement(main);
            } else {
                this.removeElement(main);
            }
        }
    }
}
