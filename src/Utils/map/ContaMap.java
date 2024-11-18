package Utils.map;

import models.Conta;

import java.util.HashMap;
import java.util.Map;

public class ContaMap implements IContaMap{
    private final Map<Long, Conta> contasMapPorNumero;
    private final Map<Long, Conta> contasMapPorCpf;

    public ContaMap() {
        this.contasMapPorNumero = new HashMap<>();
        this.contasMapPorCpf = new HashMap<>();
    }

    @Override
    public void Cadastro(Conta conta) {

        this.contasMapPorNumero.put(conta.getNumeroConta(), conta);
        this.contasMapPorCpf.put(conta.getTitular().getCpf(), conta);
    }

    @Override
    public Conta consultaNumeroConta(Long NumeroConta) {
        return this.contasMapPorNumero.get(NumeroConta);
    }

    @Override
    public Conta ConsultarCpf(Long cpf) {
        return this.contasMapPorCpf.get(cpf);
    }
}
