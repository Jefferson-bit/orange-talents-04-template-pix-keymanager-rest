package br.com.zup.jefferson.grpcclient

import br.com.zup.jefferson.ConsultaDadosChavePixServiceGrpc
import br.com.zup.jefferson.ListaChavesPixGrpc
import br.com.zup.jefferson.PixServiceGrpc
import br.com.zup.jefferson.RemoveChavePixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory( @GrpcChannel("pix") val channel: ManagedChannel) {

    @Singleton
    fun cadastra() = PixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deleta() = RemoveChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consulta() = ConsultaDadosChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun lista() = ListaChavesPixGrpc.newBlockingStub(channel)
}