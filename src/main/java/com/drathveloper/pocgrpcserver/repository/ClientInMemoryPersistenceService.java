package com.drathveloper.pocgrpcserver.repository;

import com.drathveloper.pocgrpcserver.mapper.ClientMapper;
import com.drathveloper.pocgrpcserver.model.Client;
import com.drathveloper.pocgrpcserver.service.ClientPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ClientInMemoryPersistenceService implements ClientPersistenceService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::clientEntityToClient).toList();
    }

    @Override
    public Client save(Client client) {
        var updatedClient = clientRepository.save(clientMapper.clientToEntity(client));
        return clientMapper.clientEntityToClient(updatedClient);
    }
}
