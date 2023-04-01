package io.github.gaming32.jsonentityanimation.api;

import io.github.gaming32.jsonentityanimation.mixin.client.HierarchicalModelAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public final class JsonAnimator {
    private final HierarchicalModel<?> model;

    public JsonAnimator(HierarchicalModel<?> model) {
        this.model = model;
    }

    public void animate(AnimationState state, ResourceLocation animationId, float animationProgress) {
        animate(state, animationId, animationProgress, 1f);
    }

    public void animate(AnimationState state, ResourceLocation animationId, float animationProgress, float timeSpeed) {
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
