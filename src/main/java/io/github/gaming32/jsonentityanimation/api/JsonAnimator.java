package io.github.gaming32.jsonentityanimation.api;

import io.github.gaming32.jsonentityanimation.mixin.client.HierarchicalModelAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import org.jetbrains.annotations.NotNull;

/**
 * There should be one of these per Model instance.
 */
@Environment(EnvType.CLIENT)
public final class JsonAnimator {
    private final HierarchicalModel<?> model;

    /**
     * Creates a {@code JsonAnimator} associated with an entity model.
     * @param model The associated entity model.
     */
    public JsonAnimator(@NotNull HierarchicalModel<?> model) {
        this.model = model;
    }

    /**
     * Animate the model with the specified animation and {@code AnimationState}.
     * @param state The {@code AnimationState} used for animation state.
     * @param animationId The animation to use.
     * @param animationProgress The {@code animationProgress} parameter passed to {@code setAngles}.
     */
    public void animate(@NotNull AnimationState state, @NotNull ResourceLocation animationId, float animationProgress) {
        animate(state, animationId, animationProgress, 1f);
    }

    /**
     * Animate the model with the specified animation and {@code AnimationState}.
     * @param state The {@code AnimationState} used for animation state.
     * @param animationId The animation to use.
     * @param animationProgress The {@code animationProgress} parameter passed to {@code setAngles}.
     * @param timeSpeed The speed at which to animate the model.
     */
    public void animate(@NotNull AnimationState state, @NotNull ResourceLocation animationId, float animationProgress, float timeSpeed) {
        state.updateTime(animationProgress, timeSpeed);
        state.ifStarted(state1 -> KeyframeAnimations.animate(
            model,
            JsonEntityAnimation.getAnimationOrThrow(animationId),
            state1.getAccumulatedTime(),
            1f,
            HierarchicalModelAccessor.getAnimationVectorCache()
        ));
    }
}
