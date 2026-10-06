package com.secureaccess;

public class UsuarioInexistenteException extends AutenticacaoException {
    public UsuarioInexistenteException() { super("Usuário inexistente"); }
}
