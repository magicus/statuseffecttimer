package se.icus.mag.statuseffecttimer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;

public class StatusEffectTimerRenderer {
    public void drawStatusEffectOverlay(MinecraftClient client, DrawContext context, StatusEffectInstance statusEffectInstance, int x, int y) {
        String duration = getDurationAsString(statusEffectInstance);
        int durationLength = client.textRenderer.getWidth(duration);
        context.drawTextWithShadow(client.textRenderer, duration, x + 13 - (durationLength / 2), y + 14, 0x99FFFFFF);

        int amplifier = statusEffectInstance.getAmplifier();
        if (amplifier > 0) {
            // Convert to roman numerals if possible
            String amplifierString = (amplifier < 10) ? I18n.translate("enchantment.level." + (amplifier + 1)) : "**";
            int amplifierLength = client.textRenderer.getWidth(amplifierString);
            context.drawTextWithShadow(client.textRenderer, amplifierString, x + 22 - amplifierLength, y + 3, 0x99FFFFFF);
        }
    }

    private String getDurationAsString(StatusEffectInstance statusEffectInstance) {
        if (statusEffectInstance.isInfinite()) {
            return I18n.translate("effect.duration.infinite");
        }

        int ticks = MathHelper.floor((float) statusEffectInstance.getDuration());
        int seconds = ticks / 20;

        if (seconds >= 3600) {
            return seconds / 3600 + "h";
        } else if (seconds >= 60) {
            return seconds / 60 + "m";
        } else {
            return String.valueOf(seconds);
        }
    }
}
