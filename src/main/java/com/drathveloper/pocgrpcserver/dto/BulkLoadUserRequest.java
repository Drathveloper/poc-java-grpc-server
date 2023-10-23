package com.drathveloper.pocgrpcserver.dto;

import java.util.List;

public record BulkLoadUserRequest(List<UserDto> users) {
}
