package br.com.zup.jefferson.chavepix.lista

import br.com.zup.jefferson.ListaChavesPixGrpc
import br.com.zup.jefferson.ListaChavesPixResponse
import br.com.zup.jefferson.enums.TipoDeContaRequest
import br.com.zup.jefferson.grpcclient.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChavesPixControllerTest{

    @field:Inject
    lateinit var grpcClient: ListaChavesPixGrpc.ListaChavesPixBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var  httpClient: HttpClient

    @Test
    fun `deveria retorna todas as chaves pix de um cliente`(){
        val clienteId = UUID.randomUUID().toString()

//        ListaRequest(
//            clienteId = clienteId ,
//            tipoDeConta = TipoDeContaRequest.CONTA_CORRENTE,
//            tipodeChave = Tipo,
//            chavePix = ,
//            pixId =
//        )
//
//        ListaChavesPixResponse.newBuilder()
//
//            .build()
//
//        BDDMockito.given(grpcClient.cadastra(BDDMockito.any())).willReturn(chavePixResponseGrpc)


        val request = HttpRequest.GET<String>("/api/v1/consulta/cliente/{clienteId}/pix")
        val exchange = httpClient.toBlocking().exchange(request, ListaRequest::class.java)


    }

}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class MockStubFactory {
    @Singleton
    fun stubMockito() = Mockito.mock(ListaChavesPixGrpc.ListaChavesPixBlockingStub::class.java)
}