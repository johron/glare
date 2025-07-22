# Glare <img src="https://github.com/johron/glare/blob/5e0db155deab2ded24daa0f748a93f4f33fc8a7d/src/main/resources/me/johanrong/glare/icon/glare_1024.png" alt="icon" width="28" height="28" style="vertical-align:middle;">
- A modular, cross-platform 3D game engine built in Kotlin using LWJGL and OpenGL

## TODO
- [ ] Add new shader system, with builtin lighting and stuff?!
  - [ ] Have to change Renderer so that it loops over all nodes and then gives each node to a sub-renderer. Then implement a Light Renderer sub-renderer

## Dependencies and frameworks used
- Java 22
- [LWJGL 3.3.3](https://www.lwjgl.org/)
    - OpenGL 4.6
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
        lateinit var engine: Engine
    }

    lateinit var input: Input

    override fun init(root: Node) {
        engine = (root.getComponent(Component.ENGINE_REF) as EngineRefComponent).getEngine()
        input = Input(engine)

        val camera = Node.builder {
            name = "Freecam"
            transform = Transform(Euler(0.0, 0.0, -90.0))
            parent = root
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(FreecamScript()))
            )
        }

        engine.setCamera(camera)

        Node.builder {
            name = "Node"
            transform = Transform(0.0, 0.0, -5.0)
            parent = root
            components = mutableListOf(
                MeshComponent("/model/cube.obj"),
                TextureComponent("texture/map.png"),
                ShaderComponent.Builder()
                    .vertex("/shader/mesh.vert")
                    .fragment("/shader/mesh.frag")
                    .build()
            )
        }
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
