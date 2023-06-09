# Json Entity Animation

Json Entity Animation (JsonEA) for short is a library mod for other mods to be able to define animations through JSON instead of through code. [A Blockbench plugin](animation_to_jsonea.js) is also provided in order to export your animations to this format.

## For developers

Add the mod as a dependency using the [Modrinth Maven](https://docs.modrinth.com/docs/tutorials/maven).

```gradle
dependencies {
    include(modImplementation("maven.modrinth:json-entity-animation:0.2+1.19.2"))
}
```

Once added, put your animation JSONs in the `assets/modid/jsonea` directory. To load the animations, your entity model must subclass `SinglePartEntityModel`/`HierarchicalModel`. Then define a `JsonAnimator` field and animation IDs.

```java
public class MyEntityModel extends SinglePartEntityModel<MyEntity> {
    private static final Identifier PUNCH_ANIMATION = new Identifier("modid", "my_entity/punch");
    
    private final JsonAnimator animator = new JsonAnimator(this);
    
    // ...
    
    @Override
    public void setAngles(MyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        getPart().traverse().forEach(ModelPart::resetTransform);
        animator.animate(entity.punchState, PUNCH_ANIMATION, animationProgress);
    }
}
```
