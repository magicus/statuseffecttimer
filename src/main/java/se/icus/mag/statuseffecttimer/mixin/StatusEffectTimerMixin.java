package se.icus.mag.statuseffecttimer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
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
@Mixin(value = InGameHud.class, priority = 500)
public abstract class StatusEffectTimerMixin {
	@Unique
	private StatusEffectTimerRenderer renderer = new StatusEffectTimerRenderer();

	@Shadow @Final
	private MinecraftClient client;

	@Inject(method = "renderStatusEffectOverlay",
			at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", shift = At.Shift.AFTER))
    private void appendOverlayDrawing(DrawContext context, RenderTickCounter tickCounter, CallbackInfo c,
                                      @Local List<Runnable> list, @Local StatusEffectInstance statusEffectInstance,
                                      @Local(ordinal = 4) int x, @Local(ordinal = 3) int y) {
        list.add(() -> {
            renderer.drawStatusEffectOverlay(client, context, statusEffectInstance, x, y);
        });
	}
}
