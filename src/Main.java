import Utils.Utils;
import models.Conta;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private static final Utils utils = new Utils();
    private static Conta contaAtiva = null;

    public static Conta getContaAtiva() {
        return contaAtiva;
    }

    public static void setContaAtiva(Conta contaAtiva) {
        Main.contaAtiva = contaAtiva;
    }

    public static void main(String[] args) {
        do {
            System.out.println("------- LKRA_Bank -------");
            System.out.println("1 - Login \n2 - Criar conta\n0 - Sair");
            switch (sc.nextLine()) {
                case "1":
                    setContaAtiva(utils.Login());
                    if (contaAtiva != null) {
                        System.out.println(getContaAtiva().toString());
                    }
                    break;
                case "2":
                    utils.cadastro();
                    setContaAtiva(utils.Login());
                    break;
                case "0":
                    System.out.println("Saindo...");
                    return;
                default:
                    utils.error("Opção invalida", "Responda com 1 ou 2");
            }
        } while (contaAtiva == null);

        while (true) {
            int action = utils.displayIfos(contaAtiva);
            switch (action) {
                case 0:
                    setContaAtiva(null);
                    System.out.println("Saindo...");
                    main(args);
                    return;
                case 1:
                    Double valorDeposito = utils.getvalor("\nInforme o valor a depositar:\n R$ ");
                    contaAtiva.Depositar(valorDeposito);
                    break;
                case 2:
                    Double valorSaque = utils.getvalor("\nInforme o valor a Sacar:\n R$ ");
                    Long senhaSaque = utils.getSenha("Iforme a senha para Sacar");
                    contaAtiva.Sacar(valorSaque,senhaSaque);
                    break;
                case 3:
                    Double valorDepositoCP = utils.getvalor("\nInforme o valor para depositar na poupança:\n R$ ");
                    Long senhaDepositoCP = utils.getSenha("Iforme sua senha");
                    contaAtiva.getCP().DepositarCP(valorDepositoCP,senhaDepositoCP);
                    break;
                case 4:
                    Double valorSaqueCP = utils.getvalor("\nInforme o valor para sacar da poupança:\n R$ ");
                    Long senhaSaqueCP = utils.getSenha("Iforme sua senha");
                    contaAtiva.getCP().SacarCP(valorSaqueCP,senhaSaqueCP);
                    break;
                case 5:
                    Integer TotalMesesSimulacao = null;
                    while (TotalMesesSimulacao == null) {
                        try {
                            System.out.println("Informe o total de meses para a simulação (Max: 12): ");
                            TotalMesesSimulacao = sc.nextInt();
                            sc.nextLine();

                            if (TotalMesesSimulacao > 12) {
                                utils.error("O maximo de messes é 12", "Informe um total de meses menor que 12");
                                TotalMesesSimulacao = null;
                            }
                        } catch (InputMismatchException e) {
                            utils.error("Formato invalido", "Informe apenas numeros");
                            sc.nextLine();
                        }
                    }
                    if (contaAtiva.getSaldo() < 100) {
                        utils.error("Saldo insuficiente", "O rendimento requer no minimo 100 R$ de saldo ");
                        break;
                    }
                    System.out.println(contaAtiva.SimularRendimnto(TotalMesesSimulacao));
                    break;
                case 6:
                    Integer TotalMesesExtrato = null;
                    while (TotalMesesExtrato == null) {
                        try {
                            System.out.println("Informe o total de meses para a simulação (Max: 12): ");
                            TotalMesesExtrato = sc.nextInt();
                            sc.nextLine();

                            if (TotalMesesExtrato > 12) {
                                utils.error("O maximo de messes é 12", "Informe um total de meses menor que 12");
                                TotalMesesExtrato = null;
                            }
                        } catch (InputMismatchException e) {
                            utils.error("Formato invalido", "Informe apenas numeros");
                            sc.nextLine();
                        }
                    }

                    System.out.println(contaAtiva.printExtrato(TotalMesesExtrato));
                    break;
                case 7:
                    Long senhaOriginal = utils.getSenha("Iforme sua senha");
                    if (!contaAtiva.VerificaSenha(senhaOriginal)) {
                        utils.error("Senha incorreta", "A senha informada é invalida");
                    }
                    Long newSenha = utils.getSenha("Iforme sua Nova senha (4 Digitos): ");
                    contaAtiva.AlteraSenha(newSenha,senhaOriginal);
                    setContaAtiva(null);
                    main(args);
                    return;
            }
        }
    }
}
