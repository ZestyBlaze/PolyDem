package dev.zestyblaze.demeter.event;

import com.google.common.collect.Lists;
import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.attachment.CropsInChunkAttachment;
import dev.zestyblaze.demeter.data.CropBlockData;
import dev.zestyblaze.demeter.duck.CropData;
import dev.zestyblaze.demeter.managers.DemeterCropStatsManager;
import dev.zestyblaze.demeter.mixin.ChunkMapInvoker;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.util.NewDayCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import java.util.List;
import java.util.Map;

public class LevelEvents {
    public static void init() {
        newDay();
    }

    private static void newDay() {
        NewDayCallback.EVENT.register(level -> {
            ServerChunkCache chunkCache = level.getChunkSource();
            List<LevelChunk> list = Lists.newArrayListWithCapacity(chunkCache.getLoadedChunksCount());

            ((ChunkMapInvoker)chunkCache.chunkMap).demeter$invokeForEachBlockTickingChunk(list::add);

            for (LevelChunk levelChunk : list) {
                ChunkPos chunkPos = levelChunk.getPos();

                if (level.shouldTickBlocksAt(chunkPos.pack())) {
                    for (int i = levelChunk.getMinSectionY(); i < levelChunk.getMaxSectionY(); ++i) {
                        LevelChunkSection levelChunkSection = levelChunk.getSection(levelChunk.getSectionIndexFromSectionY(i));
                        BlockPos.MutableBlockPos mutableSectionWorldOrigin = new BlockPos.MutableBlockPos();
                        BlockPos.MutableBlockPos mutableSectionWorldBlockPos = new BlockPos.MutableBlockPos();

                        CropsInChunkAttachment chunkData = levelChunk.getAttachedOrCreate(DemeterAttachments.CROPS_IN_CHUNK);
                        Map<BlockPos, CropBlockData> locations = chunkData.locations();

                        if (levelChunkSection.maybeHas(blockState -> blockState.is(BlockTags.CROPS))) {
                            int sectionWorldY = SectionPos.sectionToBlockCoord(i);

                            mutableSectionWorldOrigin.set(
                                    SectionPos.sectionToBlockCoord(levelChunk.getPos().x()),
                                    sectionWorldY,
                                    SectionPos.sectionToBlockCoord(levelChunk.getPos().z()));

                            for (int yOffset = 0; yOffset < 16; yOffset++) {
                                for (int zOffset = 0; zOffset < 16; zOffset++) {
                                    for (int xOffset = 0; xOffset < 16; xOffset++) {
                                        BlockState state = levelChunkSection.getBlockState(xOffset, yOffset, zOffset);

                                        mutableSectionWorldBlockPos.set(
                                                mutableSectionWorldOrigin.getX() + xOffset,
                                                mutableSectionWorldOrigin.getY() + yOffset,
                                                mutableSectionWorldOrigin.getZ() + zOffset
                                        );

                                        if (state.is(BlockTags.CROPS)) {
                                            locations.putIfAbsent(mutableSectionWorldBlockPos.immutable(), new CropBlockData());
                                        }
                                    }
                                }
                            }
                        }

                        if (levelChunkSection.maybeHas(blockState -> blockState.is(Blocks.FARMLAND))) {
                            int sectionWorldY = SectionPos.sectionToBlockCoord(i);

                            mutableSectionWorldOrigin.set(
                                    SectionPos.sectionToBlockCoord(levelChunk.getPos().x()),
                                    sectionWorldY,
                                    SectionPos.sectionToBlockCoord(levelChunk.getPos().z()));

                            for (int yOffset = 0; yOffset < 16; yOffset++) {
                                for (int zOffset = 0; zOffset < 16; zOffset++) {
                                    for (int xOffset = 0; xOffset < 16; xOffset++) {
                                        BlockState state = levelChunkSection.getBlockState(xOffset, yOffset, zOffset);
                                        mutableSectionWorldBlockPos.set(mutableSectionWorldOrigin).move(xOffset, yOffset, zOffset);

                                        if (state.is(Blocks.FARMLAND)) {
                                            if (state.hasProperty(BlockStateProperties.MOISTURE) && state.getValue(BlockStateProperties.MOISTURE) != 7) {
                                                if (level.getRandom().nextInt(100) < Demeter.config.farmlandConfig.morningDirtChance.get()) {
                                                    FarmlandBlock.turnToDirt(null, state, level, mutableSectionWorldBlockPos);
                                                }
                                            } else if (state.getValue(BlockStateProperties.MOISTURE).equals(7)) {
                                                level.setBlockAndUpdate(mutableSectionWorldBlockPos, state.setValue(BlockStateProperties.MOISTURE, 0));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (levelChunk.hasAttached(DemeterAttachments.CROPS_IN_CHUNK)) {
                        CropsInChunkAttachment chunkData = levelChunk.getAttached(DemeterAttachments.CROPS_IN_CHUNK);
                        chunkData.locations().forEach((pos, cropBlockData) -> {
                            BlockState state = level.getBlockState(pos);

                            if (state.is(BlockTags.CROPS)) {
                                if (!cropBlockData.frozen) {
                                    CropData data = DemeterCropStatsManager.getData(state.getBlock());

                                    if (data != null) {
                                        int days = data.daysToGrow();

                                        chunkData.incrementDays(pos);

                                        if (cropBlockData.age < days) {
                                            int stage = Mth.floor(data.maxAge() * ((float) chunkData.getDays(pos) / days));
                                            IntegerProperty property = data.resolveProperty(state);

                                            if (data.optionalBlockConvert().isPresent()) {
                                                if (stage == data.maxAge()) {
                                                    level.setBlockAndUpdate(pos, data.optionalBlockConvert().get().defaultBlockState());
                                                } else {
                                                    level.setBlockAndUpdate(pos, state.setValue(property, stage));
                                                }
                                            } else {
                                                level.setBlockAndUpdate(pos, state.setValue(property, stage));
                                            }
                                        }

                                        if (Demeter.config.cropConfig.doCropsWilt.get()) {
                                            if (chunkData.getDays(pos) == days + Demeter.config.cropConfig.daysToWilt.get()) {
                                                level.setBlockAndUpdate(pos, Blocks.DEAD_BUSH.defaultBlockState());
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
