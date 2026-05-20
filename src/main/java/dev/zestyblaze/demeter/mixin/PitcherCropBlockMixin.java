package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.PitcherCropBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PitcherCropBlock.class)
public class PitcherCropBlockMixin {
    @ModifyExpressionValue(
            method = "randomTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I")
    )
    private int demeter$randomTick(int original) {
        return 1;
    }
}
