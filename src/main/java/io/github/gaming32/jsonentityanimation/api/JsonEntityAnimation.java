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

public final class JsonEntityAnimation {
    @Nullable
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition getAnimation(ResourceLocation animationId) {
        return JsonEntityAnimationClient.ANIMATIONS.get(animationId);
    }

    @NotNull
    @Environment(EnvType.CLIENT)
    public static AnimationDefinition getAnimationOrThrow(ResourceLocation animationId) {
        final AnimationDefinition animation = getAnimation(animationId);
        if (animation == null) {
            throw new IllegalStateException("Missing animation " + animationId);
        }
        return animation;
    }

    @Environment(EnvType.CLIENT)
    public static AnimationDefinition parseAnimation(JsonObject json) {
        return AnimationParser.parseDefinition(json);
    }
}
