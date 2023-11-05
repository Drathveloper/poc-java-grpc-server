package com.drathveloper.pocgrpcserver.service;

import com.drathveloper.pocgrpcserver.model.Client;

import java.util.List;

public interface ClientPersistenceService {

    List<Client> findAll();
    Client save(Client client);
}
