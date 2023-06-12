package org.dksu.teststand.controller

import mu.KLogging
import org.dksu.teststand.entity.DataEntity
import org.dksu.teststand.service.DataService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("data")
class DataController(
    private val dataService: DataService
): KLogging() {
    @PostMapping("add/{count}")
    fun addData(@PathVariable count: Int) {
        logger.info("Add $count rows to data")
        val saved = dataService.addRandomData(count)
        logger.info("Saved $saved rows to data")
    }

    @GetMapping("getRandom")
    fun getWithRandomState(): Iterable<DataEntity> =
        dataService.getWithRandomState()

    @GetMapping("getRandomProcessing")
    fun getRandomProcessing(): Iterable<Int> =
        dataService.getWithProcessing()
}