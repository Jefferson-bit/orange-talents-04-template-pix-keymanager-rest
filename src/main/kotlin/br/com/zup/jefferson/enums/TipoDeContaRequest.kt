package br.com.zup.jefferson.enums

import br.com.zup.jefferson.TipoDeConta

enum class TipoDeContaRequest (val grpcAttribute: TipoDeConta) {

    CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA);
}