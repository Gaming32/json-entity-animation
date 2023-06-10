package io.github.gaming32.jsonentityanimation.mixin.client;

import net.minecraft.client.model.HierarchicalModel;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HierarchicalModel.class)
public interface HierarchicalModelAccessor {
    @Accessor("ANIMATION_VECTOR_CACHE")
    static Vector3f getAnimationVectorCache() {
        throw new AssertionError();
    }
}
