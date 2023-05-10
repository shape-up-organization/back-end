package br.com.shapeup.adapters.input.web.controller.request.post;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostWithouPhotoRequest {
    @NotNull
    @NotBlank
    @Size(max = 1000)
    private String description;
}
