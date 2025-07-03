# Glare
- Modular game engine made in Kotlin using LWJGL

## Dependencies and frameworks used
- Java 22
- [LWJGL 3.3.3](https://www.lwjgl.org/)
- [JOML 1.10.8](https://github.com/JOML-CI/JOML)
- [Obj 0.4.0](https://github.com/javagl/Obj)

## Example Usage
- Currently only has linux natives for LWJGL
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
        engine.camera = Freecam(engine.root, Transform(Euler()))

        val mesh = Loader.loadObj("/model/Untitled.obj")
        val meshNode = MeshNode("Node", engine.root, mesh, Transform(0.0, 0.0, -5.0))
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
