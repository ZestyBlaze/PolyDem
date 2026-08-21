package dev.zestyblaze.demeter.item;

import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.util.TimeUtil;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import net.fabricmc.fabric.api.networking.v1.context.PacketContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class CalenderItem extends SimplePolymerItem {
    public CalenderItem() {
        super(new Properties()
                .stacksTo(1)
                .setId(DemeterItems.createKey("calender")));
    }

    @Override
    public void modifyClientTooltip(List<Component> tooltip, ItemStack stack, PacketContext context) {
        MinecraftServer server = context.get(PacketContext.SERVER_INSTANCE);

        tooltip.add(Component.literal(String.valueOf(server.overworld().getLevelData().getGameTime())));

        //server.overworld().getLevelData().getGameTime();
    }

    /*
    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(
                Component.translatable("item.demeter.calender.desc",
                Component.translatable("season.demeter.spring").withStyle(ChatFormatting.GREEN),
                        Component.literal("1").withStyle(ChatFormatting.GREEN)
                        ).withStyle(ChatFormatting.DARK_GRAY)
        );
    }
     */
}
