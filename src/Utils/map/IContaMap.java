package Utils.map;

import models.Conta;

public interface IContaMap {

    void Cadastro(Conta conta);

    Conta consultaNumeroConta(Long NumeroConta);

    Conta ConsultarCpf(Long cpf);
}
