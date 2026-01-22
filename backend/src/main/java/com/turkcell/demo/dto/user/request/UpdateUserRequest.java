package com.turkcell.demo.dto.user.request;

public record UpdateUserRequest(
        String name,
        String city,
        String segment
) {
}
