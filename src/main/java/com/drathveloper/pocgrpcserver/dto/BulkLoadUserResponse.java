package com.drathveloper.pocgrpcserver.dto;

import java.util.List;

public record BulkLoadUserResponse(List<CreatedUserDto> createdUsers) {
}
