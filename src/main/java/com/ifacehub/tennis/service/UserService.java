package com.ifacehub.tennis.service;

import com.ifacehub.tennis.exception.TennisException;
import com.ifacehub.tennis.requestDto.UserDto;
import com.ifacehub.tennis.util.Paging;
import com.ifacehub.tennis.util.ResponseObject;
import com.ifacehub.tennis.requestDto.ResponseDTO;

public interface UserService {
    ResponseObject saveUser(UserDto userDTO) throws TennisException;

    ResponseObject authenticateUser(UserDto loginUser) throws TennisException;

    ResponseObject getUserById(Long id);

    ResponseObject updateUser(Long id, UserDto updatedUser);

    ResponseObject getAllUsers();
    
    public Boolean sendOtp(String email) throws Exception;
    
    public Boolean verifyOtp(String email,String otp) throws TennisException;
    
    public ResponseDTO changePassword(UserDto userDto) throws TennisException;
    
    
    
    
    
    
}
