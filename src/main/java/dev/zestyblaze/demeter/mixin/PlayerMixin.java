package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.zestyblaze.demeter.Demeter;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/BedRule;canSleep(Lnet/minecraft/world/level/Level;)Z")
    )
    private boolean demeter$canSleep(boolean original) {
        return Demeter.config.miscConfig.canSleepWhenever.get();
    }
}
