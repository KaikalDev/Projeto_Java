package models;

import models.Interfaces.IContaCorrente;

public class ContaCorrente extends Conta implements IContaCorrente {
    public ContaCorrente(Pessoa titular, Long numeroConta, Double saldo, Long senha) {
        super(titular, numeroConta, saldo, senha);
    }

    @Override
    public Object[][] SimularRendimnto(Integer totalMeses) {
        if (totalMeses <= 12 && this.getSaldo() >= 100) {
            /* Erro o maximo é 12 meses */
            /* Info do saldo < 100 */
            Object[][] matriz = new Object[totalMeses][2];
            Double saldoSimulacao = this.getSaldo();
            double rendimento = 0.00;

            for (int mes = 1; mes <= totalMeses; mes++) {
                if (saldoSimulacao > 100) {
                    rendimento += (saldoSimulacao / 100) * 0.05;
                    saldoSimulacao += rendimento;
                }
                matriz[mes][0] = mes;
                matriz[mes][1] = saldoSimulacao;
            }

            return matriz;
        }
        return null;
    }
}
