package com.haleby.htmx.htmxexample.lab.model

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.atomic.AtomicReference

@Component
class ChatRoom {
    private val chattersState = AtomicReference(persistentMapOf<ChatterName, Chatter>())
    private val messagesState = AtomicReference(persistentListOf<ChatMessage>())

    val chatters: List<Chatter>
        get() = chattersState.get().values.toList()

    val messages: List<ChatMessage>
        get() = messagesState.get().toList()

    operator fun get(chatterName: ChatterName): Chatter? = chattersState.get()[chatterName]

    fun post(chatterName: ChatterName, message: Message): ChatMessage {
        val chatMessage = ChatMessage(chatterName, message)
        messagesState.updateAndGet { messages ->
            messages.add(chatMessage)
        }
        return chatMessage
    }

    infix fun join(chatter: Chatter): ChatRoom {
        chattersState.updateAndGet { participants ->
            participants.put(chatter.name, chatter)
        }
        return this
    }
}

infix fun Chatter.join(room: ChatRoom) {
    room join this
}

typealias Message = String

data class ChatMessage(val chatterName: ChatterName, val message: Message, val postedAt: Date = Date())
