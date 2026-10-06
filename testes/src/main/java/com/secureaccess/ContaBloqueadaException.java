package com.secureaccess;

public class ContaBloqueadaException extends AutenticacaoException {
    public ContaBloqueadaException() { super("Conta bloqueada"); }
}
