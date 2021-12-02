package com.haleby.htmx.htmxexample.example4

import com.haleby.htmx.htmxexample.common.User
import com.haleby.htmx.htmxexample.common.UserRepository
import com.haleby.htmx.htmxexample.common.loggerFor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.annotation.PostConstruct

@RequestMapping(path = ["/examples/4"], produces = [MediaType.TEXT_HTML_VALUE])
@Controller
class Example4(private val userRepository: UserRepository) {

    private val log = loggerFor<Example4>()

    @GetMapping
    fun renderTable(model: Model): String {
        log.info("Loading main table")
        model.addAttribute("users", userRepository.findAll())
        model.addAttribute("now", Date().toInstant())
        return "example4/example4"
    }

    @GetMapping("/{userId}")
    fun getUserForm(model: Model, @PathVariable("userId") id: String): String {
        log.info("Get form for user $id")
        val user = userRepository.findById(id)
        requireNotNull(user) {
            "User not found $id"
        }
        model.addAttribute("user", user)
        model.addAttribute("now", Date().toInstant())
        return "example4/edit-user-form"
    }

    @PostMapping("/{userId}")
    fun updateUser(redirectAttributes: RedirectAttributes,
                   model: Model,
                   @PathVariable("userId") id: String,
                   @ModelAttribute("user") userDTO: UserDTO): String {
        log.info("Updating user $id")
        val user = userDTO.toDomain(id)
        userRepository.save(user)
        redirectAttributes.addFlashAttribute("message", "User $id updated")
        return renderTable(model)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable("userId") id: String): ResponseEntity<Any> {
        log.info("Deleting user $id")
        userRepository.delete(id)
        return ResponseEntity.ok().build()
    }

    @PostConstruct
    fun populateUserRepositoryWithRandomData() {
        repeat(10) {
            userRepository.save(userRepository.random())
        }
    }

    @ExceptionHandler
    fun handleException(iae: IllegalArgumentException) = ResponseEntity.notFound().build<Any>()

    private fun UserDTO.toDomain(id: String) = User(id, firstName, lastName, email)

    data class UserDTO(var id: String?, var firstName: String, var lastName: String, var email: String)
}
