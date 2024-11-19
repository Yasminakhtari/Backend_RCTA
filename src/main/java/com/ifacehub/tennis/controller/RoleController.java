package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.RoleDto;
import com.ifacehub.tennis.service.RoleService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<ResponseObject> createRole(@RequestBody RoleDto role) {
        ResponseObject response = roleService.createRole(role);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getRoleById(@PathVariable Long id) {
        ResponseObject response = roleService.getRoleById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateRole(@PathVariable Long id, @RequestBody RoleDto updatedRole) {
        ResponseObject response = roleService.updateRole(id, updatedRole);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteRole(@PathVariable Long id) {
        ResponseObject response = roleService.deleteRole(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getAllRole() {
        ResponseObject response = roleService.getAllRole();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
