package flow.ui.utils;

public final class Easings {
    private Easings() {
    }

    public static float easeOutCubic(float value) {
        float inverse = 1.0F - Math.clamp(value, 0.0F, 1.0F);
        return 1.0F - inverse * inverse * inverse;
    }

    public static float easeOutBack(float value) {
        float shifted = Math.clamp(value, 0.0F, 1.0F) - 1.0F;
        float overshoot = 1.70158F;
        return 1.0F + (overshoot + 1.0F) * shifted * shifted * shifted
            + overshoot * shifted * shifted;
    }

    public static float easeInOutCubic(float value) {
        float clamped = Math.clamp(value, 0.0F, 1.0F);
        if (clamped < 0.5F) {
            return 4.0F * clamped * clamped * clamped;
        }

        float shifted = -2.0F * clamped + 2.0F;
        return 1.0F - shifted * shifted * shifted * 0.5F;
    }
}
