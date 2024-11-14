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
            System.out.println("1 - Login \n2 - Criar conta");
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
            }
        } while (contaAtiva == null);

        System.out.println(getContaAtiva().toString());
    }
}
