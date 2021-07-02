package br.com.zup.jefferson.chavepix.lista

import br.com.zup.jefferson.*
import br.com.zup.jefferson.enums.TipoDeChaveRequest
import br.com.zup.jefferson.enums.TipoDeContaRequest
import br.com.zup.jefferson.grpcclient.GrpcClientFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChavesPixControllerTest {

    @field:Inject
    lateinit var grpcClient: ListaChavesPixGrpc.ListaChavesPixBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    val clienteId = UUID.randomUUID()

    @BeforeEach
    internal fun setUp() {
        val algo1 = algo(
            chavePix = null,
            tipodeChave = null,
            pixId = "5e450205-a979-44ed-82c4-3b8cfbba7e04",
            tipoDeConta = TipoDeContaRequest.CONTA_CORRENTE)

        val chaveEmail = ListaChavesPixResponse.ChavesPix.newBuilder()
                .setPixId(algo1.pixId)
                .setTipoDeChave(TipoDeChave.valueOf(TipoDeChaveRequest.CPF.name))
                .setChavePix("16094667050")
                .setTipoDeConta(TipoDeConta.valueOf(algo1.tipoDeConta.name))
            .build()

        val chaveCpf = ListaChavesPixResponse.ChavesPix.newBuilder()
                .setPixId(algo1.pixId)
                .setTipoDeChave(TipoDeChave.valueOf(TipoDeChaveRequest.EMAIL.name))
                .setChavePix("joao@gmail.com")
                .setTipoDeConta(TipoDeConta.valueOf(algo1.tipoDeConta.name))
            .build()

        BDDMockito.given(grpcClient.lista(Mockito.any())).willReturn(
            ListaChavesPixResponse.newBuilder()
                    .setIdCliente(clienteId.toString())
                    .addAllChavesPix(listOf(chaveCpf, chaveEmail))
                .build())
    }

    @Test
    fun `deveria retorna todas as chaves pix de um cliente`() {

        val request = HttpRequest.GET<String>("/api/v1/consulta/cliente/$clienteId/pix")
        val exchange = httpClient.toBlocking().exchange(request, List::class.java)

        assertEquals(HttpStatus.OK, exchange.status())
        assertNotNull(exchange.body())
        assertEquals(exchange.body().size, 2)
    }
}


@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class MockStubFactory {
    @Singleton
    fun stubMockito() = Mockito.mock(ListaChavesPixGrpc.ListaChavesPixBlockingStub::class.java)
}