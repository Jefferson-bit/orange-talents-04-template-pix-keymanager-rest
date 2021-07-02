package br.com.zup.jefferson.chavepix.consulta

import br.com.zup.jefferson.ConsultaDadosChavePixServiceGrpc
import br.com.zup.jefferson.ConsultaPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller(value = "/api/v1/consulta/cliente/{clienteId}")
class ConsultaChavePixController(@Inject val grpcClient: ConsultaDadosChavePixServiceGrpc.ConsultaDadosChavePixServiceBlockingStub) {

    @Get(value = "/pix/{pixId}")
    fun consulta(clienteId: UUID, pixId: String,
    ): HttpResponse<ConsultaResponse> {
        val response = grpcClient.consulta(toModelGrpc(clienteId = clienteId, pixId = pixId))

        return HttpResponse.ok(with(ConsultaResponse(clienteId = clienteId.toString(), pixId = pixId)) {
            this.of(response)
        })
    }

    private fun toModelGrpc(clienteId: UUID, pixId: String): ConsultaPixRequest? {
        return ConsultaPixRequest.newBuilder()
            .setPixId(ConsultaPixRequest.FiltroPorPixId.newBuilder()
                .setPixId(pixId)
                .setClienteId(clienteId.toString())
                .build())
            .build()
    }
}