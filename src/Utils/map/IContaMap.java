package Utils.map;

import models.Conta;
import models.Pessoa;

public interface IContaMap {

    void Cadastro(Conta conta);

    Conta consultaNumeroConta(Long NumeroConta);

    Conta ConsultarCpf(Long cpf);
}
