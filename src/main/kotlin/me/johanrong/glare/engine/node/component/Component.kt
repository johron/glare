package me.johanrong.glare.engine.node.component

import me.johanrong.glare.engine.node.component.core.CameraComponent
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import me.johanrong.glare.engine.node.component.graphics.MaterialComponent
import me.johanrong.glare.engine.node.component.graphics.MeshComponent
import me.johanrong.glare.engine.node.component.graphics.ShaderComponent
import me.johanrong.glare.engine.node.component.graphics.TextureComponent
import me.johanrong.glare.engine.node.component.lighting.DirectionalLightComponent
import me.johanrong.glare.engine.node.component.lighting.PointLightComponent
import me.johanrong.glare.engine.node.component.physics.RigidbodyComponent
import me.johanrong.glare.engine.node.component.physics.StaticbodyComponent
import me.johanrong.glare.engine.node.component.physics.collision.BoxColliderComponent

enum class Component {
    MESH,
    CAMERA,
    TEXTURE,
    SHADER,
    MATERIAL,
    SCRIPTS,

    RIGIDBODY,
    STATICBODY,

    DIRECTIONAL_LIGHT,
    POINT_LIGHT,

    BOX_COLLIDER;

    val category: Category
        get() = when (this) {
            DIRECTIONAL_LIGHT, POINT_LIGHT -> Category.LIGHT
            BOX_COLLIDER -> Category.COLLIDER

            else -> Category.MISC
        }

    companion object  {
        enum class Category {
            COLLIDER,
            LIGHT,
            MISC,
        }

        fun asArray(): Array<String> {
            return entries
                .filter { it != SCRIPTS }
                .map { it.name }
                .toTypedArray()
        }

        fun fromString(name: String): IComponent? {
            return when (name.uppercase()) {
                MESH.name -> MeshComponent()
                CAMERA.name -> CameraComponent()
                TEXTURE.name -> TextureComponent()
                SHADER.name -> ShaderComponent()
                MATERIAL.name -> MaterialComponent()
                SCRIPTS.name -> ScriptsComponent()

                RIGIDBODY.name -> RigidbodyComponent()
                STATICBODY.name -> StaticbodyComponent()

                DIRECTIONAL_LIGHT.name -> DirectionalLightComponent()
                POINT_LIGHT.name -> PointLightComponent()

                BOX_COLLIDER.name -> BoxColliderComponent()

                else -> null
            }
        }

        fun fromStringEnum(name: String): Component? {
            return entries.firstOrNull { it.name.equals(name, ignoreCase = true) }
        }
    }
}