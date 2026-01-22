package com.turkcell.demo.service;

import com.turkcell.demo.dto.user.mapper.UserMapper;
import com.turkcell.demo.dto.user.request.CreateUserRequest;
import com.turkcell.demo.dto.user.request.UpdateUserRequest;
import com.turkcell.demo.dto.user.response.UserResponse;
import com.turkcell.demo.entity.User;
import com.turkcell.demo.exception.ErrorMessages;
import com.turkcell.demo.exception.UserNotFoundException;
import com.turkcell.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(CreateUserRequest request){
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_WITH_ID, id)));
        return userMapper.toDto(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND_WITH_ID, id)));
        userMapper.update(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


}
