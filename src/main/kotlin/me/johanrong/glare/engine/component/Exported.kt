package me.johanrong.glare.engine.component

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Exported(val name: String, val mutable: Boolean = true)
