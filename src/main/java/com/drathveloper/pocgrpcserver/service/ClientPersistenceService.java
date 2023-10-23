package com.drathveloper.pocgrpcserver.service;

import com.drathveloper.pocgrpcserver.model.Client;

public interface ClientPersistenceService {

    Client save(Client client);
}
