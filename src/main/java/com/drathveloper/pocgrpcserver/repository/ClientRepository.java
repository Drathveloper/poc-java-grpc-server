package com.drathveloper.pocgrpcserver.repository;

import com.drathveloper.pocgrpcserver.entity.ClientEntity;
import com.drathveloper.pocgrpcserver.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    List<ClientEntity> findAll();
}
