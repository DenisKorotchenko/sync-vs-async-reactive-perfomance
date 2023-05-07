package org.dksu.teststand.controller

import org.dksu.teststand.service.ReactiveDifferenceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("reactive-difference")
@RestController
class ReactiveDifferenceController(
    private val differenceService: ReactiveDifferenceService,
) {
    @GetMapping("{fromState}/{toState}/{numberOfCompareRows}")
    suspend fun getDifferenceFromTo(
        @PathVariable fromState: Long,
        @PathVariable toState: Long,
        @PathVariable numberOfCompareRows: Long,
    ): Int =
        differenceService.getDifferenceFromTo(fromState, toState, numberOfCompareRows)
}