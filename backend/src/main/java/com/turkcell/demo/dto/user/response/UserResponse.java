package com.turkcell.demo.dto.user.response;

public record UserResponse(
        String userId,
        String name,
        String city,
        String segment
) {
}
