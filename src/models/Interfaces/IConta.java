package models.Interfaces;

import models.Conta;

public interface IConta {
    void Depositar(Double valor);

    boolean Sacar(Double valor, Long senha);

    Boolean VerificaSenha(Long senha);

    Boolean AlteraSenha(Long newSenha, Long senha);
}
