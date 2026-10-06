package com.secureaccess;

public class AutenticacaoService {
    private final UsuarioRepository repo;

    public AutenticacaoService(UsuarioRepository repo) { this.repo = repo; }

    public Usuario autenticar(String login, String senha) {
        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            throw new CamposObrigatoriosException();
        }

        Usuario u;
        try {
            u = repo.buscarPorLogin(login);
        } catch (RuntimeException e) {
            throw new ErroConexaoBancoException(e);
        }

        if (u == null) {
            throw new UsuarioInexistenteException();
        }
        // RF07: bloqueado não autentica, mesmo com a senha correta
        if (u.isBloqueado()) {
            throw new ContaBloqueadaException();
        }
        if (!u.getSenha().equals(senha)) {
            u.registrarFalha();
            throw new SenhaInvalidaException();
        }
        u.zerarTentativas(); // tentativas "consecutivas": sucesso zera o contador
        return u;
    }
}
