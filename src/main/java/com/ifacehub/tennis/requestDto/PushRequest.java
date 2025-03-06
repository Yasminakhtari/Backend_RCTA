package com.ifacehub.tennis.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PushRequest {

	@NotBlank
    private String title;

    @NotBlank
    private String message;
}
