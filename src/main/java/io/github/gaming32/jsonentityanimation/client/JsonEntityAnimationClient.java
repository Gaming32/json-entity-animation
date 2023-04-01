package io.github.gaming32.jsonentityanimation.client;

import com.google.gson.JsonObject;
import io.github.gaming32.jsonentityanimation.api.JsonEntityAnimation;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class JsonEntityAnimationClient implements ClientModInitializer {
    public static final String MOD_ID = "json-entity-animation";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Map<EntityType<?>, Map<ResourceLocation, AnimationDefinition>> ANIMATIONS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id("animations");
            }

            @Override
            public void onResourceManagerReload(ResourceManager manager) {
                ANIMATIONS.clear();
                for (final var entry : manager.listResources("animations", id -> id.getPath().endsWith(".json")).entrySet()) {
                    try (Reader reader = entry.getValue().openAsReader()) {
                        final JsonObject object = GsonHelper.parse(reader);
                        final ResourceLocation entityTypeId = new ResourceLocation(GsonHelper.getAsString(object, "entity"));
                        final EntityType<?> entityType = Registry.ENTITY_TYPE
                            .getOptional(new ResourceLocation(GsonHelper.getAsString(object, "entity")))
                            .orElse(null);
                        if (entityType == null) {
                            throw new IllegalArgumentException("Unknown entity type " + entityTypeId);
                        }
                        ANIMATIONS.computeIfAbsent(entityType, k -> new HashMap<>())
                            .put(entry.getKey(), JsonEntityAnimation.parseAnimation(object));
                    } catch (Exception e) {
                        LOGGER.error("Failed to load animation {}", entry.getKey(), e);
                    }
                }
                LOGGER.info("Loaded {} entity animations", ANIMATIONS.size());
            }
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
