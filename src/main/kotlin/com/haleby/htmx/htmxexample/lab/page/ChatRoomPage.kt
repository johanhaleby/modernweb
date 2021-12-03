package com.haleby.htmx.htmxexample.lab.page

import com.haleby.htmx.htmxexample.lab.model.ChatRoom
import com.haleby.htmx.htmxexample.lab.model.ChatterName
import org.intellij.lang.annotations.Language
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpSession

@RequestMapping(path = ["/chat/room"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
class ChatRoomPage(private val chatRoom: ChatRoom) {

    @GetMapping("/chatters")
    @ResponseBody
    fun chatters(model: Model, session: HttpSession): String {
        val me = session.getAttribute("chatter") as ChatterName
        return chattersTemplate.format(chatRoom.chatters.joinToString { (name) ->
            //language=HTML
            """<tr><td><i class="bi bi-person"></i>&nbsp;$name${if (me == name) "&nbsp;(you)" else ""}</td></tr>"""
        })
    }

    companion object {
        @Language("html")
        private const val chattersTemplate = """<table class="table table-hover ">%s</table>"""
    }
}