# Glare
- A modular, cross-platform game engine built in Kotlin using LWJGL and OpenGL

## TODO
- [x] Move old node types to the component system, including Mesh, material, texture, shader, ..

## Dependencies and frameworks used
- Java 22
- [LWJGL 3.3.3](https://www.lwjgl.org/)
- [JOML 1.10.8](https://github.com/JOML-CI/JOML)
- [Obj 0.4.0](https://github.com/javagl/Obj)

## Additional Setup
- Generate Constants
```bash
./gradlew generateConstants
```

## Example Usage
- Example is probably already outdated. 
```kotlin
fun main() {
    val window = Window(
        "Glare GE",
        1280,
        720,
        maximized = false,
        vSync = true,
    )

    GlareEngine(window, TestGame())
}

class TestGame : IScript {
    companion object {
        lateinit var engine: GlareEngine
    }

    override fun init(root: Node) {
        engine = (root.getComponent(Component.ENGINE_REF) as EngineRefComponent).getEngine()
        engine.setCamera(Freecam(engine.root, Transform(Euler(0.0, 0.0, -90.0))))

        val node = Node("Node", engine.root, Transform(0.0, 0.0, -5.0))
        val shader = ShaderComponent("/shader/mesh.vert", "/shader/mesh.frag")
        val texture = TextureComponent("texture/map.png")
        val mesh = MeshComponent("/model/cube.obj")
        node.addComponent(texture)
        node.addComponent(mesh)
        node.addComponent(shader)
    }

    override fun update(delta: Double) {
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)
    }

    override fun render() {}
    override fun cleanup() {}
}
```

## License
Licensed under the MIT License; please see the [license file](LICENSE) for terms.
