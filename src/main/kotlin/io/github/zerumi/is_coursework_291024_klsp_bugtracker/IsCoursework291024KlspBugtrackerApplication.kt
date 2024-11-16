package io.github.zerumi.is_coursework_291024_klsp_bugtracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext

lateinit var context: ApplicationContext

@SpringBootApplication
class IsCoursework291024KlspBugtrackerApplication

fun main(args: Array<String>) {
	context = runApplication<IsCoursework291024KlspBugtrackerApplication>(*args)
}
