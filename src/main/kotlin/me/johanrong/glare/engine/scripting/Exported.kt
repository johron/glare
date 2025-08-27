package me.johanrong.glare.engine.scripting

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Exported(val mutable: Boolean = true)
