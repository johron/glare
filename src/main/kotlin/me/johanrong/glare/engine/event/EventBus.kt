package me.johanrong.glare.engine.event

import kotlin.reflect.KClass

object EventBus {
    val listeners = mutableMapOf<KClass<out Event>, MutableList<(Event) -> Unit>>()

    inline fun <reified T : Event> subscribe(noinline listener: (T) -> Unit) {
        val eventClass = T::class
        val typedListener: (Event) -> Unit = { event -> if (event is T) listener(event) }
        listeners.getOrPut(eventClass) { mutableListOf() }.add(typedListener)
    }

    fun publish(event: Event) {
        listeners[event::class]?.forEach { it(event) }
    }

    inline fun <reified T : Event> unsubscribe(noinline listener: (T) -> Unit) {
        val eventClass = T::class
        listeners[eventClass]?.removeIf { it.hashCode() == listener.hashCode() }
    }
}