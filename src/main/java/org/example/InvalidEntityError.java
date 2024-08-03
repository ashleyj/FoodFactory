package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvalidEntityError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
