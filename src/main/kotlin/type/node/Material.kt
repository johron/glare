package me.johanrong.glare.type.node

class Material (private var texture: Texture?) {
    constructor(): this(null)

    fun getTexture(): Texture? {
        return texture
    }

    fun setTexture(texture: Texture) {
        this.texture = texture
    }

    fun setTexture(id: Int) {
        this.texture = Texture(id)
    }

    fun hasTexture(): Boolean {
        return texture != null
    }
}