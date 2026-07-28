package flow.ui.main;

import flow.ui.utils.Easings;

public final class AnimationState {
    private static final AnimationClock CONTAINER = new AnimationClock(220L);
    private static final AnimationClock TAB = new AnimationClock(190L);

    private static float hotbarPosition;
    private static long hotbarFrameNanos = System.nanoTime();
    private static boolean tabRequested;

    private AnimationState() {
    }

    public static void beginContainer() {
        CONTAINER.restart();
    }

    public static float containerProgress() {
        return CONTAINER.linearProgress();
    }

    public static float containerScale() {
        float progress = Easings.easeOutCubic(containerProgress());
        return 0.90F + progress * 0.10F;
    }

    public static void setTabRequested(boolean requested) {
        if (requested == tabRequested) {
            return;
        }
        tabRequested = requested;
        if (requested) {
            TAB.restart();
        } else {
            TAB.reverse();
        }
    }

    public static boolean shouldRenderTab() {
        return tabRequested || !TAB.isFinished();
    }

    public static float tabProgress() {
        return TAB.progress();
    }

    public static int animatedHotbarX(int targetX) {
        long now = System.nanoTime();
        float deltaSeconds = Math.min(0.05F, (now - hotbarFrameNanos) / 1_000_000_000.0F);
        hotbarFrameNanos = now;
        if (hotbarPosition == 0.0F) {
            hotbarPosition = targetX;
        }

        float response = 1.0F - (float) Math.exp(-18.0F * deltaSeconds);
        hotbarPosition += (targetX - hotbarPosition) * response;
        if (Math.abs(targetX - hotbarPosition) < 0.05F) {
            hotbarPosition = targetX;
        }
        return Math.round(hotbarPosition);
    }
}
