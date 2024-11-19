package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Role;
import com.ifacehub.tennis.repository.RoleRepository;
import com.ifacehub.tennis.requestDto.RoleDto;
import com.ifacehub.tennis.service.RoleService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public ResponseObject createRole(RoleDto roleDto) {
        try {
            Role role = Role.toEntity(roleDto);
            role.setBitDeletedFlag((byte) 0);
            role.setCreatedBy(String.valueOf(1));
            role.setCreatedOn(LocalDateTime.now());
            Role savedRole = roleRepository.save(role);
            return new ResponseObject(savedRole, "SUCCESS", HttpStatus.CREATED, "Role created successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to create role: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getRoleById(Long id) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            return new ResponseObject(role, "SUCCESS", HttpStatus.OK, "Role found");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to retrieve role: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateRole(Long id, RoleDto roleDto) {
        try {
            Role existingRole = roleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            Role updatedRole = Role.toEntity(roleDto);

            existingRole.setName(updatedRole.getName());
            existingRole.setDescription(updatedRole.getDescription());
            existingRole.setBitDeletedFlag(updatedRole.getBitDeletedFlag());
            existingRole.setUpdatedOn(LocalDateTime.now());

            Role savedRole = roleRepository.save(existingRole);
            return new ResponseObject(savedRole, "SUCCESS", HttpStatus.OK, "Role updated successfully");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to update role: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
            return new ResponseObject(null, "SUCCESS", HttpStatus.OK, "Role deleted successfully");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", "Role not found");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to delete role: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllRole() {
        try {
            List<Role> roleList = roleRepository.findAllActive();
            return new ResponseObject(roleList, "SUCCESS", HttpStatus.OK, "Role fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Role not found");
        }
    }
}
