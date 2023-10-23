package com.drathveloper.pocgrpcserver.mapper;

import com.drathveloper.grpc.CreatedUser;
import com.drathveloper.grpc.User;
import com.drathveloper.grpc.UserBulkLoadRequest;
import com.drathveloper.grpc.UserBulkLoadResponse;
import com.drathveloper.pocgrpcserver.dto.BulkLoadUserRequest;
import com.drathveloper.pocgrpcserver.dto.BulkLoadUserResponse;
import com.drathveloper.pocgrpcserver.dto.CreatedUserDto;
import com.drathveloper.pocgrpcserver.dto.UserDto;
import com.drathveloper.pocgrpcserver.entity.AddressEntity;
import com.drathveloper.pocgrpcserver.entity.ClientEntity;
import com.drathveloper.pocgrpcserver.model.Address;
import com.drathveloper.pocgrpcserver.model.Client;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Component
public class ClientMapper {

    public Client userRequestDtoToClient(User request) {
        var birthDate = LocalDate.ofInstant(
                Instant.ofEpochSecond(request.getBirthDate().getSeconds()),
                TimeZone.getDefault().toZoneId());
        Address address = new Address(
                null,
                request.getAddress().getCountry(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getAddress(),
                request.getAddress().getPostalCode());
        return new Client(
                null,
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhone(),
                birthDate,
                address);
    }

    public List<Client> userBulkLoadRequestToClientList(UserBulkLoadRequest request) {
        return request.getUsersList().stream().map(this::userRequestDtoToClient).toList();
    }

    public UserBulkLoadResponse clientModelListToUserBulkLoadResponse(List<Client> models) {
        var createdUsers = models.stream().map(this::clientModelToCreatedUserResponse).toList();
        return UserBulkLoadResponse.newBuilder()
                .addAllCreatedUsers(createdUsers)
                .build();
    }

    public CreatedUser clientModelToCreatedUserResponse(Client model) {
        return CreatedUser.newBuilder()
                .setId(model.getId())
                .setUsername(model.getUsername())
                .build();
    }

    public ClientEntity clientToEntity(Client client) {
        return new ClientEntity(
                client.getId(),
                client.getUsername(),
                client.getFullName(),
                client.getEmail(),
                client.getPhone(),
                client.getBirthDate(),
                new AddressEntity(
                        client.getAddress().id(),
                        client.getAddress().country(),
                        client.getAddress().city(),
                        client.getAddress().state(),
                        client.getAddress().address(),
                        client.getAddress().postalCode(),
                        null));
    }

    public Client clientEntityToClient(ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getUsername(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getBirthDate(),
                new Address(
                        entity.getAddress().getId(),
                        entity.getAddress().getCountry(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getState(),
                        entity.getAddress().getAddress(),
                        entity.getAddress().getPostalCode()));
    }

    public Client userDtoToClient(UserDto dto) {
        Address address = new Address(
                null,
                dto.address().country(),
                dto.address().city(),
                dto.address().state(),
                dto.address().address(),
                dto.address().postalCode());
        return new Client(
                null,
                dto.username(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.phone(),
                dto.birthDate(),
                address);
    }

    public CreatedUserDto clientToCreatedUser(Client client) {
        return new CreatedUserDto(client.getId(), client.getUsername());
    }

    public BulkLoadUserResponse clientsToBulkLoadUserResponse(List<Client> clients) {
        List<CreatedUserDto> createdUsers = clients.stream()
                .map(this::clientToCreatedUser).toList();
        return new BulkLoadUserResponse(createdUsers);
    }

    public List<Client> bulkLoadUserRequestToClients(BulkLoadUserRequest request) {
        return request.users().stream().map(this::userDtoToClient).toList();
    }
}
