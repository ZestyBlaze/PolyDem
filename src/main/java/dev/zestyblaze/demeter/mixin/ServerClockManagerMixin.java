package dev.zestyblaze.demeter.mixin;

import dev.zestyblaze.demeter.attachment.SeasonAttachment;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import dev.zestyblaze.demeter.util.TimeUtil;
import dev.zestyblaze.demeter.world.season.Season;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.clock.WorldClock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ServerClockManager.class)
public class ServerClockManagerMixin {
    @Shadow private MinecraftServer server;

    @Inject(method = "modifyClock", at = @At("TAIL"))
    private void recalculateSeason(Holder<WorldClock> clock, Consumer<? super ServerClockManager.ServerClockInstance> action, CallbackInfo ci) {
        int expectedSeason = TimeUtil.getCurrentSeason(server.overworld().getOverworldClockTime());

        if (expectedSeason == 1) {
            server.overworld().setAttached(DemeterAttachments.SEASON, new SeasonAttachment(Season.SPRING));
        } else if (expectedSeason == 2) {
            server.overworld().setAttached(DemeterAttachments.SEASON, new SeasonAttachment(Season.SUMMER));
        } else if (expectedSeason == 3) {
            server.overworld().setAttached(DemeterAttachments.SEASON, new SeasonAttachment(Season.AUTUMN));
        } else if (expectedSeason == 4) {
            server.overworld().setAttached(DemeterAttachments.SEASON, new SeasonAttachment(Season.WINTER));
        }
    }
}
