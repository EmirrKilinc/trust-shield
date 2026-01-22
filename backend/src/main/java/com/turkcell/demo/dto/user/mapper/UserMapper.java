package com.turkcell.demo.dto.user.mapper;

import com.turkcell.demo.dto.user.request.CreateUserRequest;
import com.turkcell.demo.dto.user.request.UpdateUserRequest;
import com.turkcell.demo.dto.user.response.UserResponse;
import com.turkcell.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toDto(User user){
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getCity(),
                user.getSegment()
        );
    }

    public User toEntity(CreateUserRequest request){
        return new User(
                request.name(),
                request.city(),
                request.segment()
        );
    }

    public void update(UpdateUserRequest request, User user){
        user.setName(request.name());
        user.setCity(request.city());
        user.setSegment(request.segment());
    }

}
