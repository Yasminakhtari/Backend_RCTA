package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.exception.TennisException;
import com.ifacehub.tennis.requestDto.ResponseDTO;
import com.ifacehub.tennis.requestDto.UserDto;
import com.ifacehub.tennis.service.UserService;
import com.ifacehub.tennis.util.Paging;
import com.ifacehub.tennis.util.ResponseObject;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Validated
public class AuthController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody UserDto userDTO) throws TennisException {
        ResponseObject response = userService.saveUser(userDTO);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
  
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> authenticateUser(@RequestBody UserDto loginUser) throws TennisException {
        // Delegate the authentication logic to the service layer
        ResponseObject response = userService.authenticateUser(loginUser);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @GetMapping("/userById")
    public ResponseEntity<ResponseObject> getUserById(@RequestParam Long id) {
        ResponseObject res = userService.getUserById(id);
        return new ResponseEntity<>(res, res.getHttpStatus());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser( @PathVariable Long id,@RequestBody UserDto updatedUser) {
        ResponseObject response = userService.updateUser(id,updatedUser);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<ResponseObject> getAllUser(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ){
        Paging paging = new Paging().setPage(pageNumber).setLimit(pageSize);

        // Call service to fetch all users with pagination and filters
        ResponseObject responseObject = userService.getAllUsers(
                paging, username, email, roleName);

        // Return the response entity with the ResponseObject and the corresponding HTTP status
        return new ResponseEntity<>(responseObject, responseObject.getHttpStatus());
    }
    
    //send otp
    @PostMapping("/sendotp/{email}")
    public ResponseEntity<ResponseDTO>  sendOtp(@PathVariable @Email(message="{user.email.invalid}") String email) throws Exception{
    	userService.sendOtp(email);
		return new ResponseEntity<>(new ResponseDTO(),HttpStatus.OK);
    }
    //verify otp
    @GetMapping("/verifyotp/{email}/{otp}")
    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable @Email(message="{user.email.invalid}") String email,@PathVariable String otp) throws TennisException{
    	userService.verifyOtp(email, otp);
    	return new ResponseEntity<>(new ResponseDTO(),HttpStatus.OK);
    }
    
    //chnage pass
    @PostMapping("/changePass")
    public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody UserDto userDto) throws TennisException{
    	return new ResponseEntity<>(userService.changePassword(userDto),HttpStatus.OK);
    }
    
}
