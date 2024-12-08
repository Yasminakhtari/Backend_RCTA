package com.ifacehub.tennis.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	
	private Long roleId;
	
//	@NotBlank(message= "{user.firstname.absent}")
    private String firstName;
	
//	@NotBlank(message= "{user.lastname.absent}")
    private String lastName;
	
	@NotBlank(message= "{user.username.absent}")
    private String username;
	
//	@NotBlank(message="{user.email.absent}")
	@Email(message="{user.email.invalid}")
    private String email;
	
	@NotBlank(message="{user.password.absent}")
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "{user.password.invalid}")
    private String password;
	
    
}
