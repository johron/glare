package me.johanrong.glare.type

interface Event

class EventBus {
    private val listeners = mutableMapOf<Class<out Event>, MutableList<(Event) -> Unit>>()

    fun <T : Event> subscribe(eventType: Class<T>, listener: (T) -> Unit) {
        listeners.computeIfAbsent(eventType) { mutableListOf() }
            .add { event -> listener(event as T) }
    }

    inline fun <reified T : Event> subscribe(noinline listener: (T) -> Unit) {
        subscribe(T::class.java, listener)
    }

    fun publish(event: Event) {
        listeners[event::class.java]?.forEach { it(event) }
    }
}