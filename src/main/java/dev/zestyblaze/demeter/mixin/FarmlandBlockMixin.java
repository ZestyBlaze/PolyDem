package dev.zestyblaze.demeter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.zestyblaze.demeter.Demeter;
import dev.zestyblaze.demeter.registry.DemeterAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {
    @ModifyExpressionValue(
            method = "randomTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"
            )
    )
    private Comparable demeter$randomTick(Comparable original) {
        return 0;
    }

    @Inject(
            method = "turnToDirt",
            at = @At("TAIL")
    )
    private static void demeter$turnToDirt(Entity sourceEntity, BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
        level.getChunk(pos).getAttachedOrCreate(DemeterAttachments.CROPS_IN_CHUNK).removeLocation(pos.above());
    }

    @ModifyReturnValue(
            method = "shouldMaintainFarmland",
            at = @At("RETURN")
    )
    private static boolean demeter$shouldMaintainFarmland(boolean original) {
        return true;
    }

    @ModifyReturnValue(
            method = "isNearWater",
            at = @At("RETURN")
    )
    private static boolean demeter$isNearWater(boolean original) {
        if (Demeter.config.farmlandConfig.waterIrrigationEnabled.get()) return original;
        return false;
    }
}
