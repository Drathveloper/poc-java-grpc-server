package com.drathveloper.pocgrpcserver.controller;

import com.drathveloper.grpc.*;
import com.drathveloper.pocgrpcserver.mapper.ClientMapper;
import com.drathveloper.pocgrpcserver.model.Client;
import com.drathveloper.pocgrpcserver.service.ClientService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserGrpcController extends UserServiceGrpc.UserServiceImplBase {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @Override
    public void bulkLoad(UserBulkLoadRequest request, StreamObserver<UserBulkLoadResponse> responseObserver) {
        long startTime = System.currentTimeMillis();
        List<Client> clients = clientMapper.userBulkLoadRequestToClientList(request);
        var processedClients = clientService.processClients(clients.toArray(new Client[0]));
        responseObserver.onNext(
                clientMapper.clientModelListToUserBulkLoadResponse(processedClients));
        responseObserver.onCompleted();
        log.info("business logic execution time: {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public void bulkLoadServerStream(UserBulkLoadRequest request, StreamObserver<CreatedUser> responseObserver) {
        request.getUsersList().forEach(user -> {
            var client = clientMapper.userRequestDtoToClient(user);
            var processedClient = clientService.processClient(client);
            responseObserver.onNext(
                    clientMapper.clientModelToCreatedUserResponse(processedClient));
        });
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<User> bulkLoadClientStream(StreamObserver<UserBulkLoadResponse> responseObserver) {
        return new StreamObserver<>() {

            private final List<Client> processed = new ArrayList<>();

            @Override
            public void onNext(User user) {
                var client = clientMapper.userRequestDtoToClient(user);
                processed.add(clientService.processClient(client));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(clientMapper.clientModelListToUserBulkLoadResponse(processed));
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<User> bulkLoadBidirectionalStream(StreamObserver<CreatedUser> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(User user) {
                var client = clientMapper.userRequestDtoToClient(user);
                var processedClient = clientService.processClient(client);
                responseObserver.onNext(clientMapper.clientModelToCreatedUserResponse(processedClient));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
