package br.com.zup.jefferson.grpcclient

import br.com.zup.jefferson.PixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {

    @Singleton
    fun pixStub(@GrpcChannel("pix") channel: ManagedChannel): PixServiceGrpc.PixServiceBlockingStub {
        return PixServiceGrpc.newBlockingStub(channel)
    }
}