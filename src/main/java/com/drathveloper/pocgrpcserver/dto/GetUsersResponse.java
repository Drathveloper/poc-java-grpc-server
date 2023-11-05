package com.drathveloper.pocgrpcserver.dto;

import java.util.List;

public record GetUsersResponse(List<CreatedUserDto> users) {
}
