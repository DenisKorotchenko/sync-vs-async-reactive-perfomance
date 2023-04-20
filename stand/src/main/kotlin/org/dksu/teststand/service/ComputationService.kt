package org.dksu.teststand.service
//
//import org.dksu.teststand.dto.ComputationInputDto
////
//import org.springframework.http.HttpStatus
//import org.springframework.stereotype.Service
//import org.springframework.web.server.ResponseStatusException
//import javax.script.ScriptEngineManager
//
//@Service
//class ComputationService(
//    //val computationRepository: ComputationRepository
//) {
//    fun getResult(computationId: Long) {
//
//    }
//
//    private fun calculate(expr: String) {
//        val engine = ScriptEngineManager().getEngineByName("nashorn")
//        println(engine.eval(expr))
//    }
//
//    fun addComputation(computationInput: ComputationInputDto): Long? {
//        val entity = ComputationEntity(expr = computationInput.expr)
//        return computationRepository.save(entity).id
//    }
//
//}