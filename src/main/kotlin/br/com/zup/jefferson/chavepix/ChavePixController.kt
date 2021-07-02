package br.com.zup.jefferson.chavepix

import br.com.zup.jefferson.PixServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller(value = "/api/v1/clientes/{clienteId}/pix")
class ChavePixController(@Inject val grpc: PixServiceGrpc.PixServiceBlockingStub) {

    @Post
    fun cadastra(clienteId:UUID, @Valid @Body novaChave: NovaChavePixRequest) : HttpResponse<NovaChavePixResponse>{

        val request = novaChave.toModelGrpc(clienteId = clienteId)
        val response = grpc.cadastra(request)

        val uri = HttpResponse.uri("/api/v1/clientes/${clienteId}/chave/${response.pixId}")

        return HttpResponse.created<NovaChavePixRequest>(uri).body(NovaChavePixResponse(novaChave))
    }
}