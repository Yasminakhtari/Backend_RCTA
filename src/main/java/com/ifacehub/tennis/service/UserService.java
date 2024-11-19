package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.UserDto;
import com.ifacehub.tennis.util.Paging;
import com.ifacehub.tennis.util.ResponseObject;

public interface UserService {
    ResponseObject saveUser(UserDto userDTO);

    ResponseObject authenticateUser(UserDto loginUser);

    ResponseObject getUserById(Long id);

    ResponseObject updateUser(Long id, UserDto updatedUser);

    ResponseObject getAllUsers(Paging paging, String username, String email, String roleName);
}
