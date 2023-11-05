package com.drathveloper.pocgrpcserver.service;

import com.drathveloper.pocgrpcserver.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientPersistenceService clientPersistenceService;

    @Override
    public List<Client> findAll() {
        return clientPersistenceService.findAll();
    }

    @Override
    public List<Client> processClients(Client... clients) {
        List<Client> processedClients = new ArrayList<>();
        for (Client client : clients) {
            processedClients.add(this.processClient(client));
        }
        return processedClients;
    }

    @Override
    public Client processClient(Client client) {
        return clientPersistenceService.save(client);
    }
}
