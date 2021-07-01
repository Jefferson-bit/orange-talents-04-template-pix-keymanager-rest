package br.com.zup.jefferson.chavepix

import br.com.zup.jefferson.RegistraChavePixRequest
import br.com.zup.jefferson.TipoDeChave
import br.com.zup.jefferson.TipoDeConta
import br.com.zup.jefferson.enums.TipoDeChaveRequest
import br.com.zup.jefferson.enums.TipoDeContaRequest
import br.com.zup.jefferson.utils.ValidaChavePix
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidaChavePix
data class NovaChavePixRequest(
    @field:Size(max = 77) val chavePix: String?,
    @field:NotNull val tipoDeConta: TipoDeContaRequest?,
    @field:NotNull val tipoDeChave: TipoDeChaveRequest?
) {

    fun toModelGrpc(clienteId: UUID) : RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setIdCliente(clienteId.toString())
            .setChavePix(chavePix ?: "")
            .setTipoDeChave(tipoDeChave?.grpcAttribute ?: TipoDeChave.DESCONHECIDO_CHAVE)
            .setTipoDeConta(tipoDeConta?.grpcAttribute ?: TipoDeConta.DESCONHECIDO_CONTA)
            .build()
    }
}

class NovaChavePixResponse(novaChavePixRequest: NovaChavePixRequest){
    val tipoDeChave = novaChavePixRequest.tipoDeChave
    val tipoDeConta = novaChavePixRequest.tipoDeConta
    val chavePix = novaChavePixRequest.chavePix
}