package com.drathveloper.pocgrpcserver;

import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class PocJavaGrpcServerApplication {

    private final ApplicationContext applicationContext;

    public PocJavaGrpcServerApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        var context = SpringApplication.run(PocJavaGrpcServerApplication.class, args);
        PocJavaGrpcServerApplication application = new PocJavaGrpcServerApplication(context);
        application.startGrpcServer();
    }

    public void startGrpcServer() {
        try {
            var startMillis = System.currentTimeMillis();
            log.info("Starting gRPC server");
            var server = applicationContext.getBean(Server.class);
            server.start();
            var startedMillis = System.currentTimeMillis() - startMillis;
            log.info("Started gRPC server on port: {} in {} ms", server.getPort(), startedMillis);
            server.awaitTermination();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

}
