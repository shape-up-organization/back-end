package br.com.shapeup.common.exceptions;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class ShapeUpBaseException extends RuntimeException {
    private String message;
    private Throwable cause = null;
}
