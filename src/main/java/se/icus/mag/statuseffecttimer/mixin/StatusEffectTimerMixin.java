package se.icus.mag.statuseffecttimer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.icus.mag.statuseffecttimer.StatusEffectTimerRenderer;

// Set priority to 500, to load before default at 1000. This is to better cooperate with HUDTweaks.
@Environment(EnvType.CLIENT)
@Mixin(value = Gui.class, priority = 500)
public abstract class StatusEffectTimerMixin {
	@Unique
	private StatusEffectTimerRenderer renderer = new StatusEffectTimerRenderer();

	@Shadow @Final
	private Minecraft minecraft;

	@Inject(method = "extractEffects",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIII)V",
					shift = At.Shift.AFTER))
	private void onExtractEffects(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo c,
	                                  @Local MobEffectInstance statusEffectInstance,
	                                  @Local(ordinal = 2) int x, @Local(ordinal = 3) int y) {
		renderer.drawStatusEffectOverlay(minecraft, graphics, statusEffectInstance, x, y);
	}
}
