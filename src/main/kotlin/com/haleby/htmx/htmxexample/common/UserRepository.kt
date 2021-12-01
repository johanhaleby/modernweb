package com.haleby.htmx.htmxexample.common

import com.github.javafaker.Faker
import org.springframework.stereotype.Component

@Component
class UserRepository {

    fun random(): User {
        val faker = Faker.instance()
        return User(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress())
    }
}