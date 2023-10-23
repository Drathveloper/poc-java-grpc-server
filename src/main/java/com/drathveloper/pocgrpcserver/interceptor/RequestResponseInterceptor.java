package com.drathveloper.pocgrpcserver.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestResponseInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata metadata,
            ServerCallHandler<ReqT, RespT> next) {
        var forwardingServerCall = new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
            @Override
            public void sendMessage(RespT message) {
                log.info("Send response:\nEndpoint: {}\nMetadata: {}\nBody:{\n{}}",
                        serverCall.getMethodDescriptor().getFullMethodName(),
                        metadata,
                        message);
                super.sendMessage(message);
            }
        };
        var listener = next.startCall(forwardingServerCall, metadata);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
            @Override
            public void onMessage(ReqT message) {
                log.info("Received request:\nEndpoint: {}\nMetadata: {}\nBody:{\n{}}",
                        forwardingServerCall.getMethodDescriptor().getFullMethodName(),
                        metadata,
                        message);
                super.onMessage(message);
            }
        };
    }
}
