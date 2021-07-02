package br.com.zup.jefferson.chavepix.lista

import br.com.zup.jefferson.ListaChavesPixGrpc
import br.com.zup.jefferson.ListaChavesPixRequest
import br.com.zup.jefferson.enums.TipoDeChaveRequest
import br.com.zup.jefferson.enums.TipoDeContaRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller(value = "/api/v1/consulta/cliente/{clienteId}/pix")
class ListaChavesPixController(@Inject val grpcClient: ListaChavesPixGrpc.ListaChavesPixBlockingStub) {

    @Get
    fun lista(clienteId: UUID): HttpResponse<List<algo>> {

        val response = grpcClient.lista(ListaChavesPixRequest.newBuilder()
            .setIdCliente(clienteId.toString())
            .build())

//        val lista = response.chavesPixList.map { algo(
//            chavePix = it.chavePix,
//            tipodeChave = TipoDeChaveRequest.valueOf(it.tipoDeChave.name),
//            pixId = it.pixId,
//            tipoDeConta = TipoDeContaRequest.valueOf(it.tipoDeConta.name))
//         }

        val lista = response.chavesPixList.map { algo(
            chavePix = it.chavePix,
            tipodeChave = TipoDeChaveRequest.valueOf(it.tipoDeChave.name),
            pixId = it.pixId,
            tipoDeConta = TipoDeContaRequest.valueOf(it.tipoDeConta.name)
        ) }

        return HttpResponse.ok(lista)

    }
}