package br.com.zup.jefferson.chavepix.lista

import br.com.zup.jefferson.ListaChavesPixRequest
import br.com.zup.jefferson.ListaChavesPixResponse
import br.com.zup.jefferson.TipoDeChave
import br.com.zup.jefferson.enums.TipoDeChaveRequest
import br.com.zup.jefferson.enums.TipoDeContaRequest
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ListaRequest(chavePix: ListaChavesPixResponse.ChavesPix){
    val chavePix: String = chavePix.chavePix
    val tipodeChave: TipoDeChave = chavePix.tipoDeChave
    val pixId = chavePix.pixId
    val tipoDeConta = chavePix.tipoDeConta
}

class algo(
    val chavePix: String?,
    val tipodeChave: TipoDeChaveRequest?,
    val pixId: String,
    val tipoDeConta: TipoDeContaRequest) {


}