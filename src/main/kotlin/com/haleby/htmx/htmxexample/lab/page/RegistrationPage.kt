package com.haleby.htmx.htmxexample.lab.page

import com.haleby.htmx.htmxexample.lab.model.ChatRoom
import com.haleby.htmx.htmxexample.lab.model.Chatter
import com.haleby.htmx.htmxexample.lab.model.join
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpSession


@RequestMapping(path = ["/chat/registration"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
class RegistrationPage(private val chatRoom: ChatRoom) {

    @GetMapping
    fun registrationForm(model: Model): String {
        model.addAttribute("now", Date().toInstant())
        return "lab/registration"
    }

    @PostMapping
    fun register(model: Model, @RequestParam name: String, httpSession: HttpSession): String {
        val chatter = Chatter(name)

        chatter join chatRoom

        model.addAttribute("chatter", chatter)
        httpSession.setAttribute("chatter", chatter.name)
        return "/lab/chatroom"
    }
}