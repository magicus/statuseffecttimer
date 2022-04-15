package se.icus.mag.statuseffecttimer.mixin;

import com.google.common.collect.Ordering;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

// Set priority to 500, to load before default at 1000. This is to better cooperate with HUDTweaks.
@Environment(EnvType.CLIENT)
@Mixin(value = InGameHud.class, priority = 500)
public abstract class StatusEffectTimerMixin extends DrawableHelper {
	@Shadow @Final
	private MinecraftClient client;

	@Inject(method = "renderStatusEffectOverlay", at = @At("TAIL"))
	private void renderDurationOverlay(MatrixStack matrices, CallbackInfo c) {
		Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
		if (!collection.isEmpty()) {
			// Replicate vanilla placement algorithm to get the duration
			// labels to line up exactly right.

			int beneficialCount = 0;
			int nonBeneficialCount = 0;
			for (StatusEffectInstance statusEffectInstance : Ordering.natural().reverse().sortedCopy(collection)) {
				StatusEffect statusEffect = statusEffectInstance.getEffectType();
				if (statusEffectInstance.shouldShowIcon()) {
					int x = this.client.getWindow().getScaledWidth();
					int y = 1;

					if (this.client.isDemo()) {
						y += 15;
					}

					if (statusEffect.isBeneficial()) {
						beneficialCount++;
						x -= 25 * beneficialCount;
					} else {
						nonBeneficialCount++;
						x -= 25 * nonBeneficialCount;
						y += 26;
					}

					String duration = getDurationAsString(statusEffectInstance);
					int durationLength = client.textRenderer.getWidth(duration);
					drawStringWithShadow(matrices, client.textRenderer, duration, x + 13 - (durationLength / 2), y + 14, 0x99FFFFFF);

					int amplifier = statusEffectInstance.getAmplifier();
					if (amplifier > 0) {
						// Most langages has "translations" for amplifier 1-5, converting to roman numerals
						String amplifierString = (amplifier < 6) ? I18n.translate("potion.potency." + amplifier) : "**";
						int amplifierLength = client.textRenderer.getWidth(amplifierString);
						drawStringWithShadow(matrices, client.textRenderer, amplifierString, x + 22 - amplifierLength, y + 3, 0x99FFFFFF);
					}
				}
			}
		}
	}

	@NotNull
	private String getDurationAsString(StatusEffectInstance statusEffectInstance) {
		int ticks = MathHelper.floor((float) statusEffectInstance.getDuration());
		int seconds = ticks / 20;

		if (ticks > 32147) {
			// Vanilla considers everything above this to be infinite
			return "**";
		} else if (seconds > 60) {
			return seconds / 60 + "m";
		} else {
			return String.valueOf(seconds);
		}
	}
}
