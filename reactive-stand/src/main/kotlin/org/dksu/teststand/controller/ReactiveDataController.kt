package org.dksu.teststand.controller

import mu.KLogging
import org.dksu.teststand.service.DataService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

//@RestController
//@RequestMapping("data")
class ReactiveDataController(
    private val dataService: DataService
): KLogging() {
    @PostMapping("add/{count}")
    suspend fun addData(@PathVariable count: Int) {
        logger.info("Add $count rows to data")
        val saved = dataService.addRandomData(count)
        logger.info("Saved $saved rows to data")
    }
}