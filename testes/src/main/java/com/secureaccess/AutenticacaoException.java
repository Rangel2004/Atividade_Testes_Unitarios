package com.secureaccess;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException(String mensagem) { super(mensagem); }
    public AutenticacaoException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
