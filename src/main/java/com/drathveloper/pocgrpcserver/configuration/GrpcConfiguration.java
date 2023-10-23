package com.drathveloper.pocgrpcserver.configuration;

import com.drathveloper.pocgrpcserver.controller.UserGrpcController;
import com.drathveloper.pocgrpcserver.interceptor.RequestResponseInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfiguration {

    @Bean
    public Server grpcServer(
            @Value("${server.grpc.port:50051}") int port,
            UserGrpcController userGrpcController) {
        return ServerBuilder.forPort(port)
                .intercept(requestResponseInterceptor())
                .addService(userGrpcController)
                .addService(ProtoReflectionService.newInstance())
                .build();
    }

    @Bean
    public RequestResponseInterceptor requestResponseInterceptor() {
        return new RequestResponseInterceptor();
    }
}
