package dev.zestyblaze.demeter.event;

import dev.zestyblaze.demeter.registry.DemeterAttachments;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BlockEvents {
    public static void init() {
        blockBreak();
    }

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
