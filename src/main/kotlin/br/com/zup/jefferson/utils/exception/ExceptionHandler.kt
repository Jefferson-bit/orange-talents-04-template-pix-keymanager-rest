package br.com.zup.jefferson.utils.exception

import br.com.zup.jefferson.ErroDetails
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.hateoas.JsonError
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
class ExceptionHandler : ExceptionHandler<StatusRuntimeException, HttpResponse<Any>> {
    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException?): HttpResponse<Any> {

        val statusCode = exception!!.status.code
        val description = exception.status.description

        val httpStatusErro = when (statusCode) {

            Status.NOT_FOUND.code -> HttpStatusException(HttpStatus.NOT_FOUND,
                description)
            Status.INVALID_ARGUMENT.code -> HttpStatusException(HttpStatus.BAD_REQUEST,
                "Dados da requisição estão invalidos")
            Status.ALREADY_EXISTS.code -> HttpStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                description)
            Status.FAILED_PRECONDITION.code -> HttpStatusException(HttpStatus.BAD_REQUEST,
                description)
            else -> {
                HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível completar a chamada")
            }
        }

        return HttpResponse.status<JsonError>(httpStatusErro.status).body(JsonError(httpStatusErro.message))
    }

}

