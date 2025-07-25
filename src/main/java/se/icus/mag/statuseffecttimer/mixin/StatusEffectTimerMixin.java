package se.icus.mag.statuseffecttimer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

// Set priority to 500, to load before default at 1000. This is to better cooperate with HUDTweaks.
@Environment(EnvType.CLIENT)
@Mixin(value = InGameHud.class, priority = 500)
public abstract class StatusEffectTimerMixin {
	@Shadow @Final
	private MinecraftClient client;

	@Inject(method = "renderStatusEffectOverlay",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIIII)V",
					shift = At.Shift.AFTER))
	private void appendOverlayDrawing(DrawContext context, RenderTickCounter tickCounter, CallbackInfo c,
									  @Local StatusEffectInstance statusEffectInstance,
									  @Local(ordinal = 2) int x, @Local(ordinal = 3) int y) {
		drawStatusEffectOverlay(context, statusEffectInstance, x, y);
	}

	private void drawStatusEffectOverlay(DrawContext context, StatusEffectInstance statusEffectInstance, int x, int y) {
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
