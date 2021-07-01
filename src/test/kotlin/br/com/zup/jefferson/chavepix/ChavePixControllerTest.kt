package br.com.zup.jefferson.chavepix

import br.com.zup.jefferson.PixServiceGrpc
import br.com.zup.jefferson.RegistraChavePixResponse
import br.com.zup.jefferson.enums.TipoDeChaveRequest
import br.com.zup.jefferson.enums.TipoDeContaRequest
import br.com.zup.jefferson.grpcclient.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
internal class ChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: PixServiceGrpc.PixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var  httpClient: HttpClient

    @Test
    fun `deveria retornar 201 created`() {

        val novaChaveRequest = NovaChavePixRequest(
            chavePix = "rodrigo@gmail.com",
            tipoDeConta = TipoDeContaRequest.CONTA_CORRENTE,
            tipoDeChave = TipoDeChaveRequest.EMAIL
        )

        val chavePixResponseGrpc = RegistraChavePixResponse.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setChavePix(novaChaveRequest.chavePix)
            .build()

        BDDMockito.given(grpcClient.cadastra(BDDMockito.any())).willReturn(chavePixResponseGrpc)

        val request = HttpRequest.POST("/api/v1/clientes/${UUID.randomUUID()}/chave", novaChaveRequest)
        val httpClientRequest = httpClient.toBlocking().exchange(request, NovaChavePixRequest::class.java)
        val httpClientResponse = httpClient.toBlocking().retrieve(request, NovaChavePixResponse::class.java)

        assertEquals(TipoDeContaRequest.CONTA_CORRENTE, httpClientResponse.tipoDeConta)
        assertEquals(TipoDeChaveRequest.EMAIL, httpClientResponse.tipoDeChave)
        assertEquals(novaChaveRequest.chavePix, httpClientResponse.chavePix)

        assertEquals(HttpStatus.CREATED, httpClientRequest.status)
        assertTrue(httpClientRequest.headers.contains("Location"))
        assertTrue(httpClientRequest.header("Location").contains(chavePixResponseGrpc.pixId))
    }
}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class MockStubFactory {
    @Singleton
    fun stubMockito() = Mockito.mock(PixServiceGrpc.PixServiceBlockingStub::class.java)
}