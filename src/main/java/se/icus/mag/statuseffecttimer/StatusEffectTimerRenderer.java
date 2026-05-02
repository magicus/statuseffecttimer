package se.icus.mag.statuseffecttimer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.util.Mth;

public class StatusEffectTimerRenderer {
    public void drawStatusEffectOverlay(Minecraft client, GuiGraphicsExtractor context, MobEffectInstance statusEffectInstance, int x, int y) {
        String duration = getDurationAsString(statusEffectInstance);
        int durationLength = client.font.width(duration);
        context.text(client.font, duration, x + 13 - (durationLength / 2), y + 14, 0x99FFFFFF);

        int amplifier = statusEffectInstance.getAmplifier();
        if (amplifier > 0) {
            // Convert to roman numerals if possible
            String amplifierString = (amplifier < 10) ? I18n.get("enchantment.level." + (amplifier + 1)) : "**";
            int amplifierLength = client.font.width(amplifierString);
            context.text(client.font, amplifierString, x + 22 - amplifierLength, y + 3, 0x99FFFFFF);
        }
    }

    private String getDurationAsString(MobEffectInstance statusEffectInstance) {
        if (statusEffectInstance.isInfiniteDuration()) {
            return I18n.get("effect.duration.infinite");
        }

        int ticks = Mth.floor((float) statusEffectInstance.getDuration());
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
