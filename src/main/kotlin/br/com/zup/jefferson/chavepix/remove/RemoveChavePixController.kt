package br.com.zup.jefferson.chavepix.remove

import br.com.zup.jefferson.RemoveChavePixRequest
import br.com.zup.jefferson.RemoveChavePixServiceGrpc
import br.com.zup.jefferson.utils.ValidaChavePix
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Validated
@Controller(value = "/api/v1/clientes/{clienteId}/pix")
class RemoveChavePixController(@Inject val grpcRemove: RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub) {

    @Delete
    fun remove(clienteId: UUID, @Valid @Body removePixRequest: RemovePixRequest) : HttpResponse<RemovePixRequest>{
        val response = removePixRequest.toModelGrpc(clienteId)
        grpcRemove.remove(response)
        return HttpResponse.noContent()
    }
}

@Introspected
@ValidaChavePix
data class RemovePixRequest(@field:NotBlank val pixId: String?) {
    fun toModelGrpc(clienteId: UUID): RemoveChavePixRequest? {
        return RemoveChavePixRequest.newBuilder()
            .setIdCliente(clienteId.toString())
            .setPixId(pixId)
            .build()
    }
}
