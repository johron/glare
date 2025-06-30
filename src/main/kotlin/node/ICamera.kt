package me.johanrong.glare.node

import me.johanrong.glare.type.Transform

interface ICamera {
    fun getTransform(): Transform
    fun setTransform(transform: Transform)
}