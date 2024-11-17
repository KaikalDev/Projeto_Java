package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transacao {
    private Date dateTime;
    private Double Valor;
    private boolean isSaque;
    private boolean isCP;
    private Double SaldoCC;
    private Double SaldoCP;

    public Transacao(Date dateTime, Double valor, boolean isSaque, boolean isCP, Double saldoCC, Double saldoCP) {
        this.dateTime = dateTime;
        Valor = valor;
        this.isSaque = isSaque;
        this.isCP = isCP;
        SaldoCC = saldoCC;
        SaldoCP = saldoCP;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public void setSaque(boolean saque) {
        isSaque = saque;
    }

    public boolean isCP() {
        return isCP;
    }

    public void setCP(boolean CP) {
        isCP = CP;
    }

    public Double getSaldoCC() {
        return SaldoCC;
    }

    public void setSaldoCC(Double saldoCC) {
        SaldoCC = saldoCC;
    }

    public Double getSaldoCP() {
        return SaldoCP;
    }

    public void setSaldoCP(Double saldoCP) {
        SaldoCP = saldoCP;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formatoData.format(dateTime);

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
