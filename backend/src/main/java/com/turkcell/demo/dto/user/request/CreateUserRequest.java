package com.turkcell.demo.dto.user.request;

public record CreateUserRequest(
        String name,
        String city,
        String segment

) {
}
