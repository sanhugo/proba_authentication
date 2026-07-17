package ru.proba.authentication.service.internal;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import ru.proba.authentication.records.UserToken;
import ru.proba.authentication.service.AuthService;
import ru.proba.authentication.service.RedisService;

@GrpcService
public class InternalService extends InternalServiceGrpc.InternalServiceImplBase {

    AuthService service;
    RedisService redisService;

    @Override
    public void updateToken(SessionId request,
                            StreamObserver<Token> responseObserver) {
        UserToken tokens = service.update(request.getSessionId());
        redisService.saveToken(tokens, request.getSessionId());
    }
}
