package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.zestyblaze.demeter.Demeter;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @ModifyExpressionValue(
            method = "startSleepInBed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/level/Level;)Z")
    )
    private boolean demeter$startSleepInBed(boolean original) {
        return Demeter.config.miscConfig.canSleepWhenever.get();
    }


}
