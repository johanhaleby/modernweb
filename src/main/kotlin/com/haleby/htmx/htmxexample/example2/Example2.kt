package com.haleby.htmx.htmxexample.example2

import com.haleby.htmx.htmxexample.common.UserRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RequestMapping(path = ["/examples/2"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
// Explain custom thymeleaf dialect: https://github.com/ultraq/thymeleaf-layout-dialect
class Example2(private val userRepository: UserRepository) {

    @GetMapping
    fun start(model: Model): String {
        model.addAttribute("user", userRepository.random())
        model.addAttribute("now", Date().toInstant())
        return "example2/example2"
    }
}
