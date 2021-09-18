package br.senac.devweb.api.product.exceptions;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorPayLoad {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}