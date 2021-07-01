package br.com.zup.jefferson.chavepix.consulta

import br.com.zup.jefferson.ConsultaDadosChavePixServiceGrpc
import br.com.zup.jefferson.ConsultaPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import java.util.*
import javax.inject.Inject

@Controller(value = "/api/v1/consultaChaves")
class ConsultaChavePixController(@Inject val grpcClient: ConsultaDadosChavePixServiceGrpc.ConsultaDadosChavePixServiceBlockingStub) {

    @Get
    fun consulta(
        @QueryValue(defaultValue = "") clienteId: UUID,
        @QueryValue(defaultValue = "") pixId: String,
    ): HttpResponse<ConsultaChaveResponse> {

        val request = toModelGrpc(clienteId = clienteId, pixId = pixId)
        val response = grpcClient.consulta(request)

        return HttpResponse.ok(with(ConsultaChaveResponse(clienteId = clienteId.toString(), pixId = pixId)) {
            this.of(response)
        })
    }

    private fun toModelGrpc(clienteId: UUID, pixId: String): ConsultaPixRequest? {
        val filtro = ConsultaPixRequest.FiltroPorPixId.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId.toString())
            .build()
        return ConsultaPixRequest.newBuilder()
            .setPixId(filtro)
            .build()
    }

}