package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.RoleDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface RoleService {
    ResponseObject createRole(RoleDto role);

    ResponseObject getRoleById(Long id);

    ResponseObject updateRole(Long id, RoleDto updatedRole);

    ResponseObject deleteRole(Long id);

    ResponseObject getAllRole();
}
