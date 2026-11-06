package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.registry.DemeterItems;
import dev.zestyblaze.demeter.util.TimeUtil;
import dev.zestyblaze.demeter.world.season.Season;
import eu.pb4.sidebars.api.Sidebar;
import eu.pb4.sidebars.api.SidebarUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.BlankFormat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
    @Unique private final Player player = (Player)(Object)this;

    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/level/Level;)Z")
    )
    private boolean demeter$canSleep(boolean original) {
        return Demeter.config.miscConfig.canSleepWhenever.get();
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void demeter$tick(CallbackInfo ci) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == DemeterItems.CALENDER ||
                    player.getItemInHand(InteractionHand.OFF_HAND).getItem() == DemeterItems.CALENDER) {
                Season season = player.level().getAttachedOrCreate(DemeterAttachments.SEASON).getSeason();
                long gameTime = serverPlayer.level().getOverworldClockTime();
                int days = TimeUtil.getElapsedDays(gameTime);
                int years = TimeUtil.getElapsedYears(gameTime);

                Sidebar sidebar = Demeter.SEASON_BOSSBAR;

                sidebar.setTitle(Component.literal("(" +
                        TimeUtil.shortName(TimeUtil.getWeekday(gameTime)) + ")" + "  " +
                        TimeUtil.formatTime((int) TimeUtil.getTimeOfDay(gameTime)))
                        .withStyle(ChatFormatting.GRAY)
                );
                sidebar.setLine(2,
                        Component.translatable("sidebar.demeter.year", Component.literal(
                                String.valueOf(years + 1)), days + 1)
                );
                sidebar.setLine(1, Component.translatable("sidebar.demeter.season",
                        Component.translatable("season.demeter." + season.getSerializedName())
                                .withStyle(Season.relatedChatColor(season)))
                        .withStyle(ChatFormatting.GRAY)
                );
                sidebar.setLine(0, Component.translatable("sidebar.demeter.weather",
                        Component.literal("Clear").withStyle(ChatFormatting.WHITE))
                        .withStyle(ChatFormatting.GRAY)
                );
                sidebar.setDefaultNumberFormat(BlankFormat.INSTANCE);

                SidebarUtils.addSidebar(serverPlayer.connection, Demeter.SEASON_BOSSBAR);
            } else {
                SidebarUtils.removeSidebar(serverPlayer.connection, Demeter.SEASON_BOSSBAR);
            }
        }
    }
}
