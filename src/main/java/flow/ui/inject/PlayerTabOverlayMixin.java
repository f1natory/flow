package flow.ui.inject;

import flow.ui.main.AnimationState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
abstract class PlayerTabOverlayMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void flowui$beginTabAnimation(GuiGraphics graphics, int width, Scoreboard scoreboard,
                                            Objective objective, CallbackInfo ci) {
        float progress = AnimationState.tabProgress();
        graphics.pose().pushMatrix();
        graphics.pose().translate(0.0F, -(1.0F - progress) * (graphics.guiHeight() * 0.55F + 24.0F));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void flowui$endTabAnimation(GuiGraphics graphics, int width, Scoreboard scoreboard,
                                          Objective objective, CallbackInfo ci) {
        graphics.pose().popMatrix();
    }
}
