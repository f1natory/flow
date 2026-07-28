package flow.ui.inject;

import flow.ui.main.AnimationState;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private PlayerTabOverlay tabList;

    @ModifyArg(
        method = "renderItemHotbar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
            ordinal = 1
        ),
        index = 2
    )
    private int flowui$animateSelectedSlot(int targetX) {
        return AnimationState.animatedHotbarX(targetX);
    }

    @Inject(method = "renderTabList", at = @At("HEAD"), cancellable = true)
    private void flowui$renderAnimatedTab(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Scoreboard scoreboard = minecraft.level.getScoreboard();
        Objective objective = scoreboard.getDisplayObjective(DisplaySlot.LIST);
        boolean requested = minecraft.options.keyPlayerList.isDown()
            && (!minecraft.isLocalServer()
            || minecraft.player.connection.getListedOnlinePlayers().size() > 1
            || objective != null);

        AnimationState.setTabRequested(requested);
        tabList.setVisible(requested);
        if (AnimationState.shouldRenderTab()) {
            graphics.nextStratum();
            tabList.render(graphics, graphics.guiWidth(), scoreboard, objective);
        }
        ci.cancel();
    }
}
