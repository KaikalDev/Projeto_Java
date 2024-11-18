package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transacao {
    private final Date date;
    private Double Valor;
    private final boolean isSaque;
    private final boolean isCP;
    private final Double SaldoCC;
    private final Double SaldoCP;

    public Transacao(Date date, Double valor, boolean isSaque, boolean isCP, Double saldoCC, Double saldoCP) {
        this.date = date;
        Valor = valor;
        this.isSaque = isSaque;
        this.isCP = isCP;
        SaldoCC = saldoCC;
        SaldoCP = saldoCP;
    }

    public Date getDateTime() {
        return date;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double valor) {
        Valor = valor;
    }

    public boolean isSaque() {
        return isSaque;
    }

    public boolean isCP() {
        return isCP;
    }

    public Double getSaldoCC() {
        return SaldoCC;
    }

    public Double getSaldoCP() {
        return SaldoCP;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formatoData.format(date);

        String header = "\n------ Transação ------\n";

        return header + (isCP ? (
                        isSaque ? (
                                "Data: " + dataFormatada + " | Valor: R$ " + Valor + "\n"
                                + "- Conta Poupança"
                                ) : (
                                "Data: " + dataFormatada + " | Valor: R$ " + Valor + "\n"
                                + "+ Conta Poupança"
                                )
                    ) : (
                        isSaque ? (
                                "Data: " + dataFormatada + " | Valor: R$ " + Valor + "\n"
                                + "- Conta Corrente"
                                ) : (
                                "Data: " + dataFormatada + " | Valor: R$ " + Valor + "\n"
                                + "+ Conta Corrente"
                                )
                    )) + "\n" + "-".repeat(header.length()) + "\n";
    }
}
