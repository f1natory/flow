package flow.ui.main;

import flow.ui.utils.Easings;

public final class AnimationClock {
    private final long durationNanos;
    private long startedAtNanos = System.nanoTime();
    private boolean reversed;

    public AnimationClock(long durationMillis) {
        durationNanos = durationMillis * 1_000_000L;
    }

    public void restart() {
        reversed = false;
        startedAtNanos = System.nanoTime();
    }

    public void reverse() {
        float progress = rawProgress();
        reversed = true;
        startedAtNanos = System.nanoTime() - (long) ((1.0F - progress) * durationNanos);
    }

    public float progress() {
        float progress = rawProgress();
        return Easings.easeOutCubic(reversed ? 1.0F - progress : progress);
    }

    public float linearProgress() {
        float progress = rawProgress();
        return reversed ? 1.0F - progress : progress;
    }

    public boolean isFinished() {
        return rawProgress() >= 1.0F;
    }

    private float rawProgress() {
        return Math.min(1.0F, (System.nanoTime() - startedAtNanos) / (float) durationNanos);
    }
}
