package br.com.zup.jefferson.chavepix

import br.com.zup.jefferson.PixServiceGrpc
import br.com.zup.jefferson.RegistraChavePixRequest
import br.com.zup.jefferson.TipoDeChave
import br.com.zup.jefferson.TipoDeConta
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller(value = "/api/v1/clientes")
class ChavePixController(
    @Inject val repository: PixRepository,
    @Inject val grpc: PixServiceGrpc.PixServiceBlockingStub) {

    @Post
    fun cadastra(@Valid @Body request: NovaChavePixRequest) : HttpResponse<NovaChavePixRequest>{
        if (repository.existsByChavePix(request.chavePix!!)) {
            throw HttpStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Chave Existente")
        }

        val chavePix = request.toModel()
        repository.save(chavePix)
        val pixRequest = RegistraChavePixRequest.newBuilder()
            .setIdCliente(chavePix.idCliente.toString())
            .setChavePix(chavePix.chavePix)
            .setTipoDeChave(TipoDeChave.valueOf(chavePix.tipoDeChave!!.name))
            .setTipoDeConta(TipoDeConta.valueOf(chavePix.tipoDeConta!!.name))
            .build()
        grpc.cadastra(pixRequest)
        val uri = UriBuilder.of("/chaves/{id}").expand(mutableMapOf("id" to chavePix.pixId))
        return HttpResponse.created<NovaChavePixRequest>(uri).body(request)
    }
}