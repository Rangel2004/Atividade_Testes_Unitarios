package com.secureaccess;

public class Usuario {
    public static final int LIMITE_TENTATIVAS = 3;

    private final String nome;
    private final String login;
    private final String senha;
    private final String email;
    private final NivelAcesso nivel;
    private int tentativas;
    private boolean bloqueado;

    public Usuario(String nome, String login, String senha, String email, NivelAcesso nivel) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.nivel = nivel;
    }

    public void registrarFalha() {
        tentativas++;
        if (tentativas >= LIMITE_TENTATIVAS) {
            bloqueado = true;
        }
    }

    public void zerarTentativas() { tentativas = 0; }

    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getEmail() { return email; }
    public NivelAcesso getNivel() { return nivel; }
    public int getTentativas() { return tentativas; }
    public boolean isBloqueado() { return bloqueado; }
}
