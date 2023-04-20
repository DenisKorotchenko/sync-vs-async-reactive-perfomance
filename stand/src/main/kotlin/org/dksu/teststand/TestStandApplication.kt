package org.dksu.teststand

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestStandApplication

fun main(args: Array<String>) {
	runApplication<TestStandApplication>(*args)
}
