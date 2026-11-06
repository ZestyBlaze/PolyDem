package dev.zestyblaze.demeter.mixin;

import dev.zestyblaze.demeter.managers.DemeterCropSeasonManager;
import dev.zestyblaze.demeter.world.season.Season;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "appendHoverText", at = @At("TAIL"))
    private void demeter$appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display,
                                         Consumer<Component> builder, TooltipFlag tooltipFlag, CallbackInfo ci) {
        Item item = (Item)(Object)this;

        if (DemeterCropSeasonManager.itemHasSeasons(item)) {
            List<Season> seasons = DemeterCropSeasonManager.getValidSeasons(item);

            for (Season season : seasons) {
                builder.accept(Component.literal(StringUtils.capitalize(season.getSerializedName())).withStyle(Season.relatedChatColor(season)));
            }
        }
    }
}
