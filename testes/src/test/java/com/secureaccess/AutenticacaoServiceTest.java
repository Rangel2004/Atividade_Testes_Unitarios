package com.secureaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AutenticacaoServiceTest {

    private static final String SENHA_OK = "Java@12345";
    private static final String SENHA_ERRADA = "errada@123";

    private Map<String, Usuario> banco;
    private AutenticacaoService service;

    @BeforeEach
    void setUp() { // estado novo a cada teste (RNF03)
        banco = new HashMap<>();
        banco.put("maria", new Usuario("Maria", "maria", SENHA_OK, "maria@x.com", NivelAcesso.CLIENTE));
        service = new AutenticacaoService(banco::get);
    }

    @Test @DisplayName("AUT01 - Usuário e senha válidos autenticam")
    void aut01() { assertEquals("maria", service.autenticar("maria", SENHA_OK).getLogin()); }

    @Test @DisplayName("AUT02 - Usuário inexistente")
    void aut02() { assertThrows(UsuarioInexistenteException.class, () -> service.autenticar("joao", SENHA_OK)); }

    @Test @DisplayName("AUT03 - Senha incorreta")
    void aut03() { assertThrows(SenhaInvalidaException.class, () -> service.autenticar("maria", SENHA_ERRADA)); }

    @Test @DisplayName("AUT04 - Usuário nulo")
    void aut04() { assertThrows(CamposObrigatoriosException.class, () -> service.autenticar(null, SENHA_OK)); }

    @Test @DisplayName("AUT05 - Senha nula")
    void aut05() { assertThrows(CamposObrigatoriosException.class, () -> service.autenticar("maria", null)); }

    @Test @DisplayName("AUT06 - Usuário vazio")
    void aut06() { assertThrows(CamposObrigatoriosException.class, () -> service.autenticar("", SENHA_OK)); }

    @Test @DisplayName("AUT07 - Senha vazia")
    void aut07() { assertThrows(CamposObrigatoriosException.class, () -> service.autenticar("maria", "")); }

    @Test @DisplayName("AUT08 - Usuário bloqueado é rejeitado mesmo com senha correta")
    void aut08() {
        falhar(3);
        assertThrows(ContaBloqueadaException.class, () -> service.autenticar("maria", SENHA_OK));
    }

    @Test @DisplayName("AUT09 - 1ª tentativa inválida: permanece desbloqueado")
    void aut09() { falhar(1); assertFalse(banco.get("maria").isBloqueado()); }

    @Test @DisplayName("AUT10 - 2ª tentativa inválida: permanece desbloqueado")
    void aut10() { falhar(2); assertFalse(banco.get("maria").isBloqueado()); }

    @Test @DisplayName("AUT11 - 3ª tentativa inválida: bloqueia")
    void aut11() { falhar(3); assertTrue(banco.get("maria").isBloqueado()); }

    @Test @DisplayName("AUT12 - Tentativa após bloqueio é rejeitada")
    void aut12() {
        falhar(3);
        assertThrows(ContaBloqueadaException.class, () -> service.autenticar("maria", SENHA_ERRADA));
    }

    @Test @DisplayName("RF07 - Sucesso zera o contador (tentativas consecutivas)")
    void tentativasConsecutivas() {
        falhar(2);
        service.autenticar("maria", SENHA_OK);
        falhar(1);
        assertFalse(banco.get("maria").isBloqueado());
    }

    @Test @DisplayName("RF06 - Erro de conexão com o banco")
    void erroConexao() {
        AutenticacaoService s = new AutenticacaoService(l -> { throw new RuntimeException("db off"); });
        assertThrows(ErroConexaoBancoException.class, () -> s.autenticar("maria", SENHA_OK));
    }

    @Test @DisplayName("RF08 - Níveis de acesso ADMIN, GERENTE e CLIENTE existem")
    void niveisDeAcesso() {
        assertEquals(3, NivelAcesso.values().length);
        assertNotNull(NivelAcesso.valueOf("ADMIN"));
        assertNotNull(NivelAcesso.valueOf("GERENTE"));
        assertNotNull(NivelAcesso.valueOf("CLIENTE"));
    }

    private void falhar(int vezes) {
        for (int i = 0; i < vezes; i++) {
            assertThrows(SenhaInvalidaException.class, () -> service.autenticar("maria", SENHA_ERRADA));
        }
    }
}
