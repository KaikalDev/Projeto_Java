package models.Interfaces;

import models.Conta;

public interface IConta {
    void Depositar(Double valor);

    boolean Sacar(Double valor, Long senha);

    Boolean VerificaSenha(Long senha);

    void AlteraSenha(Long newSenha, Long senha);

    String SimularRendimnto(Integer totalMeses);
}
