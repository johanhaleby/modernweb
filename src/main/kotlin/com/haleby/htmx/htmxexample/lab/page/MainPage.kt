package com.haleby.htmx.htmxexample.lab.page

import com.haleby.htmx.htmxexample.lab.model.ChatRoom
import com.haleby.htmx.htmxexample.lab.model.ChatterName
import jakarta.servlet.http.HttpSession
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RequestMapping(path = ["/chat"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
class MainPage(private val chatRoom: ChatRoom) {

    @GetMapping
    fun mainPage(model: Model, session: HttpSession): String {
        model.addAttribute("now", Date().toInstant())
        val chatterName = session.getAttribute("chatter") as ChatterName?
        if (chatterName != null) {
            model.addAttribute("chatter", chatRoom[chatterName])
        }
        return "lab/main"
    }
}