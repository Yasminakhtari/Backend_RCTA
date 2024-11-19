package com.ifacehub.tennis.entity;

import com.ifacehub.tennis.requestDto.UserDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class User extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Byte bitDeletedFlag;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static User toEntity(UserDto userDto) {
        return Utils.mapper().convertValue(userDto, User.class);
    }
}
