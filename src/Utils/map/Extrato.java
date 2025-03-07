package Utils.map;

import Utils.Utils;
import models.Transacao;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Extrato {
    private final List<Transacao> transacoes;
    private static final Utils utils = new Utils();

    public Extrato() {
        this.transacoes = new ArrayList<>();
    }

    public void addToExtrato(Transacao transacao) {
        if (transacao.isSaque()) {
            transacao.setValor(transacao.getValor() * -1);
        }
        this.transacoes.add(transacao);
    }

    public String print(int totalMeses) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Calendar limite = Calendar.getInstance();
        limite.add(Calendar.MONTH, -totalMeses);

        List<Transacao> transacoesFiltradas = transacoes.stream()
                .filter(t -> t.getDateTime().after(limite.getTime()))
                .collect(Collectors.toList());

        if (transacoesFiltradas.isEmpty()) {
            utils.error("Nenhuma transação encontrada", "Nenhuma transação encontrada no intervalo especificado");
            return "";
        }

        Transacao transacaoMaisAntiga = transacoesFiltradas.get(0);
        StringBuilder extrato = new StringBuilder("--------- Extrato Bancário ---------\n");

        double saldoCC = transacaoMaisAntiga.getSaldoCC();
        double saldoCP = transacaoMaisAntiga.getSaldoCP();

        calendar.setTime(transacaoMaisAntiga.getDateTime());
        int diaAtual = calendar.get(Calendar.DAY_OF_MONTH);

        for (Transacao transacao : transacoesFiltradas) {
            calendar.setTime(transacao.getDateTime());
            int diaTransacao = calendar.get(Calendar.DAY_OF_MONTH);
            if (diaTransacao != diaAtual) {
                diaAtual = diaTransacao;
                extrato.append("\n\n------ Saldo do Dia ------\n")
                        .append("Saldo Conta Corrente: ").append(String.format("%.2f", saldoCC))
                        .append("\nSaldo Conta Poupança: ").append(String.format("%.2f", saldoCP));
            }

            if (transacao.isCP()) {
                saldoCP += transacao.getValor();
                extrato.append("\n").append(transacao.isSaque() ? "Saque - " : "Deposito - ")
                        .append("Poupança (CP): ")
                        .append(String.format("%.2f R$", transacao.getValor()));
            } else {
                saldoCC += transacao.getValor();
                extrato.append("\n\nData: ").append(formatoData.format(transacao.getDateTime()))
                        .append("\n").append(transacao.isSaque() ? "Saque - " : "Deposito - ")
                        .append("Corrente (CC): ")
                        .append(String.format("%.2f R$", transacao.getValor()));
            }
        }

        extrato.append("\n\n------ Saldo do Final ------\n")
                .append("Saldo Conta Corrente: ").append(String.format("%.2f", saldoCC))
                .append("\nSaldo Conta Poupança: ").append(String.format("%.2f", saldoCP)).append("\n\n");

        return extrato.toString();
    }
}
