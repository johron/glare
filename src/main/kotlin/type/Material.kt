package me.johanrong.glare.type

class Material (private var texture: Texture?) {
    constructor(): this(null)

    fun getTexture(): Texture? {
        return texture
    }

    fun setTexture(texture: Texture) {
        this.texture = texture
    }

    fun hasTexture(): Boolean {
        return texture != null
    }
}