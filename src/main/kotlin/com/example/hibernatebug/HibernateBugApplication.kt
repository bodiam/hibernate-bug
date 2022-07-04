package com.example.hibernatebug

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

fun main(args: Array<String>) {
    runApplication<HibernateBugApplication>(*args)
}

@SpringBootApplication
class HibernateBugApplication : ApplicationRunner {

    @Autowired
    lateinit var personRepository: PersonRepository

    override fun run(args: ApplicationArguments?) {
        personRepository.saveAll(listOf(Person("a", "a1"), Person("b", "b1"), Person("c", "c1")))
        personRepository.findPeople(Pageable.ofSize(2))
    }
}

@Repository
interface PersonRepository : JpaRepository<Person, Long> {

    @Query(nativeQuery = true)
    fun findPeople(pageable: Pageable): Page<PersonFirstName>

}

interface PersonFirstName {
    fun getName(): String
}

@Entity
class Person(

    val firstName: String,
    val lastName: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)