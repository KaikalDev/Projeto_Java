package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transacao {
    private Date dateTime;
    private Double Valor;
    private boolean isSaque;
    private boolean isCP;

    public Transacao(Date dateTime, Double valor, boolean isSaque, boolean isCP) {
        this.dateTime = dateTime;
        Valor = valor;
        this.isSaque = isSaque;
        this.isCP = isCP;
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

    @Override
    public String toString() {
        return "Transação [Data=" + new SimpleDateFormat("dd/MM/yyyy").format(dateTime) +
                ", Valor=" + Valor +
                ", Tipo=" + (isSaque ? "Saque" : "Depósito") + "]";
    }
}
