package io.github.gaming32.jsonentityanimation.api;

import com.google.gson.JsonObject;
import io.github.gaming32.jsonentityanimation.client.AnimationParser;
import io.github.gaming32.jsonentityanimation.client.JsonEntityAnimationClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JsonEntityAnimation {
    @Nullable
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition getAnimation(EntityType<?> entityType, ResourceLocation animationId) {
        final var animations = JsonEntityAnimationClient.ANIMATIONS.get(entityType);
        return animations != null ? animations.get(animationId) : null;
    }

    @NotNull
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition getAnimationOrThrow(EntityType<?> entityType, ResourceLocation animationId) {
        final AnimationDefinition animation = getAnimation(entityType, animationId);
        if (animation == null) {
            throw new IllegalStateException("Missing animation " + animationId + " for " + Registry.ENTITY_TYPE.getId(entityType));
        }
        return animation;
    }

    @Environment(EnvType.CLIENT)
    public static AnimationDefinition parseAnimation(JsonObject json) {
        return AnimationParser.parseDefinition(json);
    }
}
