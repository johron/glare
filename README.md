# Glare
- Modular game engine made in Kotlin using LWJGL

## Dependencies
- Java 22

## Example Usage
```kotlin
fun main() {
    val window = Window(
        "Test Game",
        1280,
        720,
        maximized = false,
        vSync = true
    )

    GlareEngine(window, TestGame())
}

class TestGame : IRootScript {
    companion object {
        lateinit var engine: GlareEngine
    }

    override fun init(engine: GlareEngine) {
        println("Game initialized with engine: $engine")

        TestGame.engine = engine
        engine.camera = Freecam(engine.root, Transform(Euler(0.0, 0.0, 0.0)))

        val mesh = loadObj("/model/cube.obj")
        val meshNode = MeshNode("Node", engine.root, mesh, Transform(0.0, 0.0, -5.0))
    }

    override fun update(delta: Double) {
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)
    }

    override fun render() {}

    override fun cleanup() {
        println("Game cleaned up")
    }
}
```

## License
Licensed under the MIT License; please see the [license file](LICENSE) for terms.
