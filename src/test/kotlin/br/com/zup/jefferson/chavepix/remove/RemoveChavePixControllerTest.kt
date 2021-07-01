package br.com.zup.jefferson.chavepix.remove

import br.com.zup.jefferson.RemoveChavePixResponse
import br.com.zup.jefferson.RemoveChavePixServiceGrpc
import br.com.zup.jefferson.grpcclient.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RemoveChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    fun `deveria remover chave pix do sistema pix grpc`() {

        val clienteId = UUID.randomUUID().toString()
        val removePixRequest = RemovePixRequest(pixId = UUID.randomUUID().toString())

        val responseGrpc = RemoveChavePixResponse.newBuilder()
            .setIdCliente(removePixRequest.pixId)
            .setPixId(clienteId)
            .build()

        BDDMockito.given(grpcClient.remove(Mockito.any())).willReturn(responseGrpc)

        val request = HttpRequest.DELETE("/api/v1/clientes/$clienteId/chave", removePixRequest)
        val httpClientResponse = httpClient.toBlocking().exchange(request, RemovePixRequest::class.java)

        assertEquals(HttpStatus.NO_CONTENT, httpClientResponse.status())
        assertNull(httpClientResponse.body())
    }
}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class MockStubFactory {
    @Singleton
    fun stubMockito() = Mockito.mock(RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub::class.java)
}