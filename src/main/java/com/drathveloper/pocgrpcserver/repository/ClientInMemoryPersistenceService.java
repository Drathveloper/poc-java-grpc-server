package com.drathveloper.pocgrpcserver.repository;

import com.drathveloper.pocgrpcserver.mapper.ClientMapper;
import com.drathveloper.pocgrpcserver.model.Client;
import com.drathveloper.pocgrpcserver.service.ClientPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInMemoryPersistenceService implements ClientPersistenceService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Override
    public Client save(Client client) {
        var updatedClient = clientRepository.save(clientMapper.clientToEntity(client));
        return clientMapper.clientEntityToClient(updatedClient);
    }
}
