package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CropBlock.class)
public class CropBlockMixin {
    @ModifyExpressionValue(
            method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getRawBrightness(Lnet/minecraft/core/BlockPos;I)I")
    )
    private int demeter$randomTick(int original) {
        return 0;
    }
}
