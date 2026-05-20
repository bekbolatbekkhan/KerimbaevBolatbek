package com.lab.KerimbaevBolatbek.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class KerimbaevBolatbekBookRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @Size(max = 20)
    private String isbn;

    @Size(max = 2000)
    private String description;

    @Min(value = 1000, message = "Publication year must be valid")
    @Max(value = 2100)
    private Integer publicationYear;

    @Size(max = 100)
    private String publisher;

    @NotNull(message = "Total copies is required")
    @Min(value = 0)
    private Integer totalCopies;

    private Long categoryId;

    private Set<Long> authorIds;
}