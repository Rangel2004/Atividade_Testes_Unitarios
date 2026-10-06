package com.secureaccess;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidacaoSenhaServiceTest {

    private final ValidacaoSenhaService service = new ValidacaoSenhaService();

    @Test @DisplayName("CT01 - Senha válida (11 caracteres) deve ser aceita [P8]")
    void ct01_deveAceitarSenhaValida() { assertTrue(service.validarSenha("Java@123456")); }

    @Test @DisplayName("CT02 - 9 caracteres deve ser rejeitada [P3]")
    void ct02_deveRejeitarMenorQue10() { assertFalse(service.validarSenha("Java@1234")); }

    @Test @DisplayName("CT03 - 13 caracteres deve ser rejeitada [P4]")
    void ct03_deveRejeitarMaiorQue12() { assertFalse(service.validarSenha("Java@12345678")); }

    @Test @DisplayName("CT04 - Sem número deve ser rejeitada [P5]")
    void ct04_deveRejeitarSemNumero() { assertFalse(service.validarSenha("Java@Testes")); }

    @Test @DisplayName("CT05 - Sem letra deve ser rejeitada [P6]")
    void ct05_deveRejeitarSemLetra() { assertFalse(service.validarSenha("123456@789")); }

    @Test @DisplayName("CT06 - Sem especial deve ser rejeitada [P7]")
    void ct06_deveRejeitarSemEspecial() { assertFalse(service.validarSenha("Java123456")); }

    @Test @DisplayName("CT07 - Nula deve ser rejeitada [P1]")
    void ct07_deveRejeitarNula() { assertFalse(service.validarSenha(null)); }

    @Test @DisplayName("CT08 - Vazia deve ser rejeitada [P2]")
    void ct08_deveRejeitarVazia() { assertFalse(service.validarSenha("")); }

    @Test @DisplayName("CT17 - Só espaços deve ser rejeitada [P2]")
    void ct17_deveRejeitarSoEspacos() { assertFalse(service.validarSenha("   ")); }

    @Test @DisplayName("CT18 - Especial fora do conjunto permitido deve ser rejeitada [P7]")
    void ct18_deveRejeitarEspecialNaoPermitido() { assertFalse(service.validarSenha("Java_123456")); }

    @ParameterizedTest(name = "CT09/CT10 - fronteira: {0} caracteres -> {2}")
    @CsvSource({
            "9,  Java@1234,     false",
            "10, Java@12345,    true",
            "11, Java@123456,   true",
            "12, Java@1234567,  true",
            "13, Java@12345678, false"
    })
    @DisplayName("Valores de fronteira (9, 10, 11, 12, 13)")
    void testeFronteiraTamanho(int tamanho, String senha, boolean esperado) {
        assertEquals(tamanho, senha.length(), "massa de teste com tamanho incorreto");
        assertEquals(esperado, service.validarSenha(senha));
    }
}
