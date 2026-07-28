package flow.ui.inject;

import flow.ui.main.AnimationState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreativeModeInventoryScreen.class)
abstract class CreativeInventoryScreenMixin {
    @ModifyArgs(
        method = "renderBg",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphics;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"
        )
    )
    private void flowui$animatePlayerModel(Args args) {
        GuiGraphics graphics = args.get(0);
        float scale = AnimationState.containerScale();
        float centerX = graphics.guiWidth() * 0.5F;
        float centerY = graphics.guiHeight() * 0.5F;

        args.set(1, flowui$scaleCoordinate(args.get(1), centerX, scale));
        args.set(2, flowui$scaleCoordinate(args.get(2), centerY, scale));
        args.set(3, flowui$scaleCoordinate(args.get(3), centerX, scale));
        args.set(4, flowui$scaleCoordinate(args.get(4), centerY, scale));
        args.set(5, Math.max(1, Math.round((int) args.get(5) * scale)));
    }

    private static int flowui$scaleCoordinate(int coordinate, float center, float scale) {
        return Math.round(center + (coordinate - center) * scale);
    }
}
