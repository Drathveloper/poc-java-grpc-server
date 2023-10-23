package com.drathveloper.pocgrpcserver.service;

import com.drathveloper.pocgrpcserver.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    List<Client> processClients(Client... client);

    Client processClient(Client client);
}
