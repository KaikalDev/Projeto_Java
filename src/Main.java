import Utils.Utils;
import models.Conta;

import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private static Utils utils = new Utils();
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
                    break;
                case 1:
                    System.out.print("\nDigite o valor a depositar:\n R$ ");
                    Double valor = sc.nextDouble();
                    sc.nextLine();
                    contaAtiva.Depositar(valor);
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
            }
        }
    }
}
