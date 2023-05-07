package org.dksu.teststand.controller

import org.dksu.teststand.service.DifferenceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("difference")
//@RestController
class DifferenceController(
    private val differenceService: DifferenceService,
) {
    @GetMapping("{fromState}/{toState}/{numberOfCompareRows}")
    fun getDifferenceFromTo(
        @PathVariable fromState: Long,
        @PathVariable toState: Long,
        @PathVariable numberOfCompareRows: Long,
    ): Int = differenceService.getDifferenceFromTo(fromState, toState, numberOfCompareRows)
}