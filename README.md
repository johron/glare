# Glare
- Modular game engine made in Kotlin using LWJGL

## TODO
- [x] Move old node types to the component system, including Mesh, material, texture, shader, ..

## Dependencies and frameworks used
- Java 22
- [LWJGL 3.3.3](https://www.lwjgl.org/)
- [JOML 1.10.8](https://github.com/JOML-CI/JOML)
- [Obj 0.4.0](https://github.com/javagl/Obj)

## Example Usage
- Currently only has linux natives for LWJGL, and is probably already outdated. 
```kotlin
fun main() {
    val window = Window(
        "Test Game",
        1280,
        720,
        maximized = false,
        vSync = true,
    )

    GlareEngine(window, TestGame())
}

class TestGame : IRootScript {
    companion object {
        lateinit var engine: GlareEngine
    }

    override fun init(engine: GlareEngine) {
        TestGame.engine = engine
        engine.camera = Freecam(engine.root, Transform(Euler(0.0, 0.0, -90.0)))

        val node = Node("Node", engine.root, Transform(0.0, 0.0, -5.0))
        val shader = ShaderComponent("/shader/mesh.vert", "/shader/mesh.frag")
        val texture = Loader.loadTexture("texture/map.png")
        val mesh = Loader.loadObj("/model/cube.obj")
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
