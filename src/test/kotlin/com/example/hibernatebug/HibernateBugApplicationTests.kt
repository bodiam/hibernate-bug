package com.example.hibernatebug

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable

@SpringBootTest
class HibernateBugApplicationTests {

    @Autowired
    lateinit var personRepository: PersonRepository


    @Test
    fun bug() {
        personRepository.findPeople(Pageable.ofSize(2))
    }

}
