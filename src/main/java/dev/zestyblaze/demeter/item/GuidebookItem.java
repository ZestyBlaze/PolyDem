package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.advancement.DemeterTriggers;
import dev.zestyblaze.demeter.advancement.TriggerCriterion;
import dev.zestyblaze.demeter.registry.DemeterItems;
import eu.pb4.booklet.api.item.GuideBookItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GuidebookItem extends GuideBookItem {
    public GuidebookItem() {
        super(new Item.Properties().stacksTo(1).setId(DemeterItems.createKey("demeters_guide")), Demeter.createId("main_page"));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            TriggerCriterion.trigger(serverPlayer, DemeterTriggers.GUIDEBOOK);
        }

        return super.use(level, player, hand);
    }
}
