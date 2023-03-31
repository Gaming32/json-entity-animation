package io.github.gaming32.jsonentityanimation.api;

import io.github.gaming32.jsonentityanimation.mixin.client.HierarchicalModelAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

@Environment(EnvType.CLIENT)
public final class JsonAnimator<T extends Entity> {
    private final EntityType<T> entity;
    private final HierarchicalModel<T> model;

    public JsonAnimator(EntityType<T> entity, HierarchicalModel<T> model) {
        this.entity = entity;
        this.model = model;
    }

    public void animate(AnimationState state, ResourceLocation animationId, float animationProgress) {
        animate(state, animationId, animationProgress, 1f);
    }

    public void animate(AnimationState state, ResourceLocation animationId, float animationProgress, float timeSpeed) {
        state.updateTime(animationProgress, timeSpeed);
        state.ifStarted(state1 -> KeyframeAnimations.animate(
            model,
            JsonEntityAnimation.getAnimationOrThrow(entity, animationId),
            state1.getAccumulatedTime(),
            1f,
            HierarchicalModelAccessor.getAnimationVectorCache()
        ));
    }
}
