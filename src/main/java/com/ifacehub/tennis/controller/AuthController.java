package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.UserDto;
import com.ifacehub.tennis.service.UserService;
import com.ifacehub.tennis.util.Paging;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody UserDto userDTO) {
        ResponseObject response = userService.saveUser(userDTO);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> authenticateUser(@RequestBody UserDto loginUser) {
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
}
