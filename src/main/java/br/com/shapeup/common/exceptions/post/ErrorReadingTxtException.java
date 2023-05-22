package br.com.shapeup.common.exceptions.post;
public class ErrorReadingTxtException extends RuntimeException{
    public ErrorReadingTxtException() {
        super("Error reading txt file");
    }
}
