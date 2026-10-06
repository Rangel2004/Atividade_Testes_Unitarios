package com.secureaccess;

public class SenhaInvalidaException extends AutenticacaoException {
    public SenhaInvalidaException() { super("Senha inválida"); }
}
