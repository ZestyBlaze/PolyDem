package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.StemBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StemBlock.class)
public class StemBlockMixin {
    @Shadow
    @Final
    public static int MAX_AGE;

    @ModifyExpressionValue(
            method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getRawBrightness(Lnet/minecraft/core/BlockPos;I)I")
    )
    private int demeter$randomTick(int original) {
        return 0;
    }
}
