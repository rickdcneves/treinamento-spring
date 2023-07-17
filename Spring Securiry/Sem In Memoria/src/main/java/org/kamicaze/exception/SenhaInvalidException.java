package org.kamicaze.exception;

public class SenhaInvalidException extends RuntimeException{
    public SenhaInvalidException(){
        super("Senha Invalida");
    }
}
