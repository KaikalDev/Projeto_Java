package Utils.map;

import models.Transacao;

import java.text.SimpleDateFormat;
import java.util.*;

public class Extrato {
    private final Map<Date, Transacao> extratoMap;

    public Extrato() {
        this.extratoMap = new HashMap<>();
    }

    public void addToExtrato(Transacao transacao) {
        if (transacao.isSaque()) {
            transacao.setValor(transacao.getValor() * -1);
        }
        this.extratoMap.put(transacao.getDateTime(), transacao);
    }

    public String toString(int totalMeses, Double SaldoCC, Double SaldoCP) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.MONTH, -totalMeses);

        Date diaAtual = null;

        StringBuilder extratoFormatado = new StringBuilder();
        extratoFormatado.append("--------- Extrato Bancário ---------\n");

        for (Map.Entry<Date, Transacao> mapTransacao : extratoMap.entrySet()) {
            Date data = mapTransacao.getKey();
            Transacao transacao = mapTransacao.getValue();

            if (data.before(calendario.getTime())) {
                continue;
            }

            if (!data.equals(diaAtual)) {
                if (diaAtual != null) {
                    extratoFormatado.append("Saldo Corrente do dia: R$ ")
                            .append(String.format("%.2f", SaldoCC))
                            .append("\nSaldo Poupança do dia (CP): R$ ")
                            .append(String.format("%.2f", SaldoCP))
                            .append("\n");
                }
                diaAtual = data;
                extratoFormatado.append("\nData: ").append(formatoData.format(data)).append("\n");
            }

            String tipo = transacao.isSaque() ? "Saque" : "Depósito";
            String contaTipo = transacao.isCP() ? "Poupança (CP)" : "Corrente";

            extratoFormatado.append(tipo)
                    .append(" - ")
                    .append(contaTipo)
                    .append(": R$ ")
                    .append(String.format("%.2f", transacao.getValor()))
                    .append("\n");

            if (transacao.isCP()) {
                SaldoCP += transacao.getValor();
            } else {
                SaldoCC += transacao.getValor();
            }
        }

        if (diaAtual != null) {
            extratoFormatado.append("Saldo Corrente do dia: R$ ")
                    .append(String.format("%.2f", SaldoCC))
                    .append("\nSaldo Poupança do dia (CP): R$ ")
                    .append(String.format("%.2f", SaldoCP))
                    .append("\n");
        }

        extratoFormatado.append("\n------- Saldo Atual -------\n")
                .append("Saldo Corrente: R$ ")
                .append(String.format("%.2f", SaldoCC))
                .append("\nSaldo Poupança (CP): R$ ")
                .append(String.format("%.2f", SaldoCP))
                .append("\n");

        return extratoFormatado.toString();
    }
}
