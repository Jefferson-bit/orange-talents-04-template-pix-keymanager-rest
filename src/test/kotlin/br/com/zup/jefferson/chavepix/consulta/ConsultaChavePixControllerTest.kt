package br.com.zup.jefferson.chavepix.consulta

import br.com.zup.jefferson.*
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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultaChavePixControllerTest{

    @field:Inject
    lateinit var grpcClient: ConsultaDadosChavePixServiceGrpc.ConsultaDadosChavePixServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var  httpClient: HttpClient
    lateinit var consultaresponse: ConsultaResponse

    @BeforeEach
    internal fun setUp() {

        consultaresponse = ConsultaResponse(
            clienteId = UUID.randomUUID().toString(),
            pixId =  UUID.randomUUID().toString(),
            chave = Chave(
                chavePix = "joao@gmail.com",
                tipoDeChave = TipoDeChaveRequest.EMAIL,

            ),
            conta = Conta(
                instituicao = "ITAÚ UNIBANCO S.A.",
                agencia = "0001",
                nome = "Rafael M C Ponte",
                cpf = "02467781054",
                numero = "291900",
                tipoDeConta = TipoDeContaRequest.CONTA_CORRENTE,
            )
        )

        val consultaPixResponse = ConsultaPixResponse.newBuilder()
            .setClienteId(consultaresponse.clienteId)
            .setPixId(consultaresponse.pixId)
            .setChave(ConsultaPixResponse.ChavePix.newBuilder()
                .setTipoDeChave(TipoDeChave.valueOf(consultaresponse.chave!!.tipoDeChave.name))
                .setChavePix(consultaresponse.chave!!.chavePix)
                .setConta(ConsultaPixResponse.ChavePix.Conta.newBuilder()
                    .setTipoDeConta(TipoDeConta.valueOf(consultaresponse.conta!!.tipoDeConta.name))
                    .setCpf(consultaresponse.conta!!.cpf)
                    .setNumero(consultaresponse.conta!!.numero)
                    .setNome(consultaresponse.conta!!.nome)
                    .setAgencia(consultaresponse.conta!!.agencia)
                    .setInstituicao(consultaresponse.conta!!.instituicao)
                    .build())
                .build())
            .build()

        BDDMockito.given(grpcClient.consulta(Mockito.any())).willReturn(consultaPixResponse)
    }

    @Test
    fun `deveria retorna 200 com os detalhes da chave pix`(){

        val request = HttpRequest.GET<String>(
            "/api/v1/consulta/cliente/${consultaresponse.clienteId}/pix/${consultaresponse.pixId}")

        val exchange = httpClient.toBlocking().exchange(request, ConsultaResponse::class.java)

        assertEquals(consultaresponse.clienteId, exchange.body().clienteId)
        assertEquals(consultaresponse.pixId, exchange.body().pixId)
        assertEquals(consultaresponse.chave!!.chavePix, exchange.body().chave!!.chavePix)
        assertEquals(consultaresponse.conta!!.tipoDeConta, exchange.body().conta!!.tipoDeConta)
        assertEquals(HttpStatus.OK, exchange.status())
        assertNotNull(exchange.body())
    }
}

@Factory
@Replaces(factory = GrpcClientFactory::class)
internal class MockStubFactory {
    @Singleton
    fun stubMockito() = Mockito.
    mock(ConsultaDadosChavePixServiceGrpc.ConsultaDadosChavePixServiceBlockingStub::class.java)
}
