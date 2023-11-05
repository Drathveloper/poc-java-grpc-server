package com.drathveloper.pocgrpcserver.mapper;

import com.drathveloper.grpc.CreatedUser;
import com.drathveloper.grpc.User;
import com.drathveloper.grpc.UserBulkLoadRequest;
import com.drathveloper.grpc.UserBulkLoadResponse;
import com.drathveloper.pocgrpcserver.dto.*;
import com.drathveloper.pocgrpcserver.entity.AddressEntity;
import com.drathveloper.pocgrpcserver.entity.ClientEntity;
import com.drathveloper.pocgrpcserver.model.Address;
import com.drathveloper.pocgrpcserver.model.Client;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;
import com.google.protobuf.Timestamp;

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
                .setId(model.id())
                .setUsername(model.username())
                .setFirstName(model.firstName())
                .setLastName(model.lastName())
                .setEmail(model.email())
                .setPhone(model.phone())
                .setBirthDate(localDateToTimestamp(model.birthDate()))
                .setCountry(model.address().country())
                .setCity(model.address().city())
                .setState(model.address().state())
                .setAddress(model.address().address())
                .setPostalCode(model.address().postalCode())
                .build();
    }

    public ClientEntity clientToEntity(Client client) {
        return new ClientEntity(
                client.id(),
                client.username(),
                client.firstName(),
                client.lastName(),
                client.email(),
                client.phone(),
                client.birthDate(),
                new AddressEntity(
                        client.address().id(),
                        client.address().country(),
                        client.address().city(),
                        client.address().state(),
                        client.address().address(),
                        client.address().postalCode(),
                        null));
    }

    public Client clientEntityToClient(ClientEntity entity) {
        return new Client(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
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
        return new CreatedUserDto(client.id(), client.username(), client.firstName(), client.lastName(), client.email(), client.phone(), client.birthDate(), client.address().country(),
                client.address().city(),
                client.address().state(),
                client.address().address(),
                client.address().postalCode());
    }

    public GetUsersResponse clientsToGetUsersResponse(List<Client> clients) {
        List<CreatedUserDto> createdUsers = clients.stream()
                .map(this::clientToCreatedUser).toList();
        return new GetUsersResponse(createdUsers);
    }

    public BulkLoadUserResponse clientsToBulkLoadUserResponse(List<Client> clients) {
        List<CreatedUserDto> createdUsers = clients.stream()
                .map(this::clientToCreatedUser).toList();
        return new BulkLoadUserResponse(createdUsers);
    }

    public List<Client> bulkLoadUserRequestToClients(BulkLoadUserRequest request) {
        return request.users().stream().map(this::userDtoToClient).toList();
    }

    public Timestamp localDateToTimestamp(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
