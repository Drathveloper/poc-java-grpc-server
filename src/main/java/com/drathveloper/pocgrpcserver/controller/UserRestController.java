package com.drathveloper.pocgrpcserver.controller;

import com.drathveloper.pocgrpcserver.dto.BulkLoadUserRequest;
import com.drathveloper.pocgrpcserver.dto.BulkLoadUserResponse;
import com.drathveloper.pocgrpcserver.dto.GetUsersResponse;
import com.drathveloper.pocgrpcserver.mapper.ClientMapper;
import com.drathveloper.pocgrpcserver.model.Client;
import com.drathveloper.pocgrpcserver.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @GetMapping(path = "/users")
    public ResponseEntity<GetUsersResponse> getUsers() {
        log.info("getUsers controller");
        var storedClients = clientService.findAll();
        var mappedClients = clientMapper.clientsToGetUsersResponse(storedClients);
        log.info("mappedClients" + mappedClients.toString());
        return ResponseEntity.ok(mappedClients);
    }

    @PostMapping(path = "/user/bulk")
    public ResponseEntity<BulkLoadUserResponse> bulkLoad(
            @RequestBody BulkLoadUserRequest request) {
        log.info("bulkLoad controller");
        var clients = clientMapper.bulkLoadUserRequestToClients(request);
        List<Client> processedClients = List.of();
        if (!clients.isEmpty()) {
            processedClients = clientService.processClients(clients.toArray(new Client[0]));
        }
        log.info("processedClients" + processedClients.toString());
        return ResponseEntity.ok(clientMapper.clientsToBulkLoadUserResponse(processedClients));
    }
}
