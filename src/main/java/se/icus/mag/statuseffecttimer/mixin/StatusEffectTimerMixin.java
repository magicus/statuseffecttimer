package se.icus.mag.statuseffecttimer.mixin;

import com.google.common.collect.Ordering;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

// Set priority to 500, to load before default at 1000. This is to better cooperate with HUDTweaks.
@Environment(EnvType.CLIENT)
@Mixin(value = InGameHud.class, priority = 500)
public abstract class StatusEffectTimerMixin {
	@Shadow @Final
	private MinecraftClient client;

	@Redirect(method = "renderStatusEffectOverlay",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;shouldShowIcon()Z"))
	private boolean display(StatusEffectInstance instance) {
		return shouldDisplay(instance);
	}

	private boolean shouldDisplay(StatusEffectInstance instance) {
		return true;
	}

	@Inject(method = "renderStatusEffectOverlay", at = @At("TAIL"))
	private void renderDurationOverlay(DrawContext context, CallbackInfo ci) {
		Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
		if (!collection.isEmpty()) {
			// Replicate vanilla placement algorithm to get the duration
			// labels to line up exactly right.

			int beneficialCount = 0;
			int nonBeneficialCount = 0;
			for (StatusEffectInstance statusEffectInstance : Ordering.natural().reverse().sortedCopy(collection)) {
				StatusEffect statusEffect = statusEffectInstance.getEffectType();
				if (shouldDisplay(statusEffectInstance)) {
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
					context.drawCenteredTextWithShadow(client.textRenderer, duration, x + 13, y + 14, 0x99FFFFFF);

					String amplifierString = getAmplifierAsString(statusEffectInstance);
					if (amplifierString != null) {
						context.drawCenteredTextWithShadow(client.textRenderer, amplifierString, x + 19, y + 3, 0x99FFFFFF);
					}
				}
			}
		}
	}

	@NotNull
	private String getDurationAsString(StatusEffectInstance statusEffectInstance) {
		if (statusEffectInstance.isInfinite()) {
			return I18n.translate("effect.duration.infinite");
		}
		int ticks = MathHelper.floor((float) statusEffectInstance.getDuration());
		int seconds = ticks / 20;
		int hours = seconds / 3600;
		double minutes = seconds / 60.0;
		if (seconds >= 3600) {
			if (hours < 1000) {
				return String.format("%dh", hours);
			}
			return "****";
		} else if (seconds >= 60) {
			return String.format("%.1fm", (float) minutes);
		}
		return String.format("%ds", seconds);
	}

	@Nullable
	private String getAmplifierAsString(StatusEffectInstance statusEffectInstance) {
		int ampl = statusEffectInstance.getAmplifier();
		String k = String.format("enchantment.level.%d", ampl + 1);
		if (ampl > 0) {
			if (I18n.hasTranslation(k)) {
				return I18n.translate(k);
			} else {
				return "**";
			}
		}
		return null;
	}
}
