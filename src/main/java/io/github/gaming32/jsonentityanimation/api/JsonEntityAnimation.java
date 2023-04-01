package io.github.gaming32.jsonentityanimation.api;

import com.google.gson.JsonObject;
import io.github.gaming32.jsonentityanimation.client.AnimationParser;
import io.github.gaming32.jsonentityanimation.client.JsonEntityAnimationClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * General static API for Json Entity Animation
 */
public final class JsonEntityAnimation {
    /**
     * Get the animation with the specified ID.
     * @param animationId The ID of the animation.
     * @return The animation, or null if not found.
     */
    @Nullable
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition getAnimation(@Nullable ResourceLocation animationId) {
        return JsonEntityAnimationClient.ANIMATIONS.get(animationId);
    }

    /**
     * Get the animation with the specified ID.
     * @param animationId The ID of the animation.
     * @return The animation
     * @throws IllegalStateException If the specified animation is not found
     */
    @NotNull
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition getAnimationOrThrow(@NotNull ResourceLocation animationId) throws IllegalStateException {
        final AnimationDefinition animation = getAnimation(animationId);
        if (animation == null) {
            throw new IllegalStateException("Missing animation " + animationId);
        }
        return animation;
    }

    /**
     * Parses the specified {@link JsonObject} into an animation
     * @param json The {@link JsonObject} to parse
     * @return The parsed animation
     * @throws IllegalArgumentException If the specified {@link JsonObject} does not represent a valid JsonEA animation
     */
    @NotNull
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition parseAnimation(@NotNull JsonObject json) throws IllegalArgumentException {
        return AnimationParser.parseDefinition(json);
    }
}
