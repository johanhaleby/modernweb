package com.haleby.htmx.htmxexample.example3

import com.haleby.htmx.htmxexample.common.User
import com.haleby.htmx.htmxexample.common.UserRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.lang.IllegalArgumentException
import java.util.*

@RequestMapping(path = ["/examples/3"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
class Example3(private val userRepository: UserRepository) {

    @GetMapping
    fun start(model: Model): String {
        val user = userRepository.save(userRepository.random())
        model.addAttribute("user", user)
        model.addAttribute("now", Date().toInstant())
        return "example3/example3"
    }

    @GetMapping("/{userId}")
    fun getUser(model: Model, @PathVariable("userId") id: String): String {
        val user = userRepository.findById(id)
        requireNotNull(user) {
            "User not found $id"
        }
        model.addAttribute("user", user)
        model.addAttribute("now", Date().toInstant())
        return "example3/example3"
    }

    @PostMapping("/{userId}")
    fun updateUser(redirectAttributes: RedirectAttributes, @PathVariable("userId") id: String, @ModelAttribute("user") userDTO: UserDTO): String {
        val user = userDTO.toDomain(id)
        userRepository.save(user)
        redirectAttributes.addFlashAttribute("message", "User updated")
        return "redirect:/examples/3/$id"
    }

    @ExceptionHandler
    fun handleException(iae: IllegalArgumentException) = ResponseEntity.notFound().build<Any>()

    private fun UserDTO.toDomain(id: String) = User(id, firstName, lastName, email)

    data class UserDTO(var id: String?, var firstName: String, var lastName: String, var email: String)
}
