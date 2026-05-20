package dev.zestyblaze.demeter.event;

import dev.zestyblaze.demeter.registry.DemeterAttachments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class BlockEvents {
    public static void init() {
        //blockPlace();
        blockBreak();
    }

    /*
    private static void blockPlace() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState().is(BlockTags.CROPS)) {
                level.getChunk(hitResult.getBlockPos().above()).getAttachedOrCreate(DemeterAttachments.CROPS_IN_CHUNK)
                        .addLocation(hitResult.getBlockPos().above());
            }
            return InteractionResult.PASS;
        });
    }
     */

    private static void blockBreak() {
        PlayerBlockBreakEvents.AFTER.register((level, _, pos, state, _) -> {
            if (!level.isClientSide()) {
                if (state.is(BlockTags.CROPS)) {
                    level.getChunk(pos).getAttachedOrCreate(DemeterAttachments.CROPS_IN_CHUNK)
                            .removeLocation(pos);
                }
            }
        });
    }
}
