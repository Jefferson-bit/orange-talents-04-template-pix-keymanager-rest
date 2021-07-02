package br.com.zup.jefferson.chavepix.consulta

import br.com.zup.jefferson.ConsultaPixResponse
import br.com.zup.jefferson.enums.TipoDeChaveRequest
import br.com.zup.jefferson.enums.TipoDeContaRequest

class ConsultaResponse(
    val clienteId: String,
    val pixId: String,
    val chave: Chave? = null,
    val conta: Conta? = null
) {
    fun of(consultaResponseGrpc: ConsultaPixResponse): ConsultaResponse {
        return ConsultaResponse(
            clienteId = consultaResponseGrpc.clienteId,
            pixId = consultaResponseGrpc.pixId,
            chave = Chave(
                chavePix = consultaResponseGrpc.chave.chavePix,
                tipoDeChave = TipoDeChaveRequest.valueOf(consultaResponseGrpc.chave.tipoDeChave.name)
            ),
            conta = Conta(
                instituicao = consultaResponseGrpc.chave.conta.instituicao,
                agencia = consultaResponseGrpc.chave.conta.agencia,
                nome = consultaResponseGrpc.chave.conta.nome,
                cpf = consultaResponseGrpc.chave.conta.cpf,
                numero = consultaResponseGrpc.chave.conta.numero,
                tipoDeConta = TipoDeContaRequest.valueOf(consultaResponseGrpc.chave.conta.tipoDeConta.name)
            )
        )
    }
}

class Chave(
    val chavePix: String,
    val tipoDeChave: TipoDeChaveRequest,
)

class Conta(
    val instituicao: String,
    val agencia: String,
    val nome: String,
    val cpf: String,
    val numero: String,
    val tipoDeConta: TipoDeContaRequest,
)