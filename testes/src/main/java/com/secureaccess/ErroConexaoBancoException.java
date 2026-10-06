package com.secureaccess;

public class ErroConexaoBancoException extends AutenticacaoException {
    public ErroConexaoBancoException(Throwable causa) { super("Erro de conexão com o banco", causa); }
}
