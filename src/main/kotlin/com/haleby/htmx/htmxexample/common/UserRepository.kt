package com.haleby.htmx.htmxexample.common

import com.github.javafaker.Faker
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Component
class UserRepository {
    private val state: ConcurrentMap<String, User> = ConcurrentHashMap()

    fun findById(id: String): User? = state[id]

    fun save(user: User): User = user.also { state[user.id] = it }

    fun delete(id: String) {
        state.remove(id)
    }

    fun random(): User {
        val faker = Faker.instance()
        return User(faker.idNumber().valid(), faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress())
    }

    fun findAll(): List<User> = state.values.toList()
}