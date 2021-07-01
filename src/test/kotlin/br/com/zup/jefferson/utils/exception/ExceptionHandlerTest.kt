package br.com.zup.jefferson.utils.exception

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ExceptionHandlerTest{

    val httpRequest = HttpRequest.GET<Any>("/")

    @Test
    fun `deveria retornar 404 quando ocorrer um not found no servidor grpc`(){

        val notFound = StatusRuntimeException(Status.NOT_FOUND)
        val response = ExceptionHandler().handle(httpRequest, notFound)

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.status)
        Assertions.assertNotNull(response.body())
    }

    @Test
    fun `deveria retornar 400 quando ocorrer um invalid argument no servidor grpc`(){
        val invalidArgument = StatusRuntimeException(Status.INVALID_ARGUMENT)
        val response = ExceptionHandler().handle(httpRequest, invalidArgument)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.status)
        Assertions.assertNotNull(response.body())
    }

    @Test
    fun `deveria retornar 422 quando ocorrer um already exists no servidor grpc`(){
        val alreadyExists = StatusRuntimeException(Status.ALREADY_EXISTS)
        val response = ExceptionHandler().handle(httpRequest, alreadyExists)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        Assertions.assertNotNull(response.body())
    }


    @Test
    fun `deveria retornar 400 quando ocorrer um failed precondition no servidor grpc`(){
        val failedPrecondition = StatusRuntimeException(Status.FAILED_PRECONDITION)
        val response = ExceptionHandler().handle(httpRequest, failedPrecondition)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.status)
        Assertions.assertNotNull(response.body())
    }

    @Test
    fun `deveria retornar 500 quando ocorrer um unknown no servidor grpc`(){
        val unknown = StatusRuntimeException(Status.UNKNOWN)
        val response = ExceptionHandler().handle(httpRequest, unknown)

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        Assertions.assertNotNull(response.body())
    }

}