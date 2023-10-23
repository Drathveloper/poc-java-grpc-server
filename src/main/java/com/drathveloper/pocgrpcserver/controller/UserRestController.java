package com.drathveloper.pocgrpcserver.controller;

import com.drathveloper.pocgrpcserver.dto.BulkLoadUserRequest;
import com.drathveloper.pocgrpcserver.dto.BulkLoadUserResponse;
import com.drathveloper.pocgrpcserver.mapper.ClientMapper;
import com.drathveloper.pocgrpcserver.model.Client;
import com.drathveloper.pocgrpcserver.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @PostMapping(path = "/user/bulk")
    public ResponseEntity<BulkLoadUserResponse> bulkLoad(
            @RequestBody BulkLoadUserRequest request) {
        var clients = clientMapper.bulkLoadUserRequestToClients(request);
        var processedClients = clientService.processClients(clients.toArray(new Client[0]));
        return ResponseEntity.ok(clientMapper.clientsToBulkLoadUserResponse(processedClients));
    }
}
