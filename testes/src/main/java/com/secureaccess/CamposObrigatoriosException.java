package com.secureaccess;

public class CamposObrigatoriosException extends AutenticacaoException {
    public CamposObrigatoriosException() { super("Usuário e senha são obrigatórios"); }
}
