package org.dksu.teststand.controller

import org.dksu.teststand.dto.ComputationInputDto
//import org.dksu.teststand.service.ComputationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("computation")
class ComputationController(
    //val computationService: ComputationService
) {
    @GetMapping("{computationId}")
    fun getDifferenceSize(@PathVariable computationId: Long) = ""
        //computationService.getResult(computationId)

    @PostMapping("")
    fun addComputation(@RequestBody computationInput: ComputationInputDto) = ""
        //computationService.addComputation(computationInput)
}