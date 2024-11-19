package com.ifacehub.tennis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifacehub.tennis.requestDto.RoleDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Role extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    private Byte bitDeletedFlag;

    public static Role toEntity(RoleDto roleDto) {
        return Utils.mapper().convertValue(roleDto, Role.class);
    }
}
