package Utils.map;

import models.Conta;

import java.util.HashMap;
import java.util.Map;

public class ContaMap implements IContaMap{
    private final Map<Long, Conta> contasPorNumero;
    private final Map<Long, Conta> contasPorCpf;

    public ContaMap() {
        this.contasPorNumero = new HashMap<>();
        this.contasPorCpf = new HashMap<>();
    }

    @Override
    public void Cadastro(Conta conta) {

        this.contasPorNumero.put(conta.getNumeroConta(), conta);
        this.contasPorCpf.put(conta.getTitular().getCpf(), conta);
    }

    @Override
    public Conta consultaNumeroConta(Long NumeroConta) {
        return this.contasPorNumero.get(NumeroConta);
    }

    @Override
    public Conta ConsultarCpf(Long cpf) {
        return this.contasPorCpf.get(cpf);
    }
}
