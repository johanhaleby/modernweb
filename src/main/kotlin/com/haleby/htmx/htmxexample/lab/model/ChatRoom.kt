package com.haleby.htmx.htmxexample.lab.model

import kotlinx.collections.immutable.persistentMapOf
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicReference


@Component
class ChatRoom {
    private val stateRef = AtomicReference(persistentMapOf<ChatterName, Chatter>())

    val chatters: List<Chatter>
        get() = stateRef.get().values.toList()

    operator fun get(chatterName: ChatterName): Chatter? = stateRef.get()[chatterName]

    operator fun plus(chatter: Chatter): ChatRoom {
        stateRef.updateAndGet { participants ->
            participants.put(chatter.name, chatter)
        }
        return this
    }
}

infix fun Chatter.join(room: ChatRoom) {
    room + this
}
