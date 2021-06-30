package br.com.zup.jefferson.utils

import javax.persistence.Embeddable
import javax.validation.constraints.NotBlank

@Embeddable
class Conta(
    @field:NotBlank val instituicao: String,
    @field:NotBlank val agencia: String,
    @field:NotBlank val numeroDaConta: String,
    @field:NotBlank val nomeTitular: String,
    @field:NotBlank val cpfTitular: String,
) {
    companion object {
        val ITAU_UNIBANCO_SA_ISPB = "60701190"
    }
}

data class ContaResponse(
    val tipo: String,
    val instituicao: InstituicaoResponse,
    val agencia: String,
    val numero: String,
    val titular: TitularResponse,

    ) {

    fun toModel(): Conta {
        return Conta(
            instituicao = this.instituicao.nome,
            agencia = agencia,
            numeroDaConta = this.numero,
            nomeTitular = this.titular.nome,
            cpfTitular = this.titular.cpf)
    }

}

data class TitularResponse(val nome: String, val cpf: String) {}

data class InstituicaoResponse(val nome: String, val ispb: String) {}