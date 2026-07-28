package flow.ui.inject;

import flow.ui.main.AnimationState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
abstract class ContainerScreenMixin {
    @Inject(
        method = "renderBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V"
        )
    )
    private void flowui$beginBackgroundAnimation(GuiGraphics graphics, int mouseX, int mouseY,
                                                   float partialTick, CallbackInfo ci) {
        flowui$pushContainerTransform(graphics);
    }

    @Inject(
        method = "renderBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V",
            shift = At.Shift.AFTER
        )
    )
    private void flowui$endBackgroundAnimation(GuiGraphics graphics, int mouseX, int mouseY,
                                                 float partialTick, CallbackInfo ci) {
        graphics.pose().popMatrix();
    }

    @Inject(method = "renderContents", at = @At("HEAD"))
    private void flowui$beginContainerAnimation(GuiGraphics graphics, int mouseX, int mouseY,
                                                  float partialTick, CallbackInfo ci) {
        flowui$pushContainerTransform(graphics);
    }

    private static void flowui$pushContainerTransform(GuiGraphics graphics) {
        float scale = AnimationState.containerScale();
        float centerX = graphics.guiWidth() * 0.5F;
        float centerY = graphics.guiHeight() * 0.5F;

        graphics.pose().pushMatrix();
        graphics.pose().translate(centerX, centerY);
        graphics.pose().scale(scale, scale);
        graphics.pose().translate(-centerX, -centerY);
    }

    @Inject(method = "renderContents", at = @At("RETURN"))
    private void flowui$endContainerAnimation(GuiGraphics graphics, int mouseX, int mouseY,
                                                float partialTick, CallbackInfo ci) {
        graphics.pose().popMatrix();
    }
}
