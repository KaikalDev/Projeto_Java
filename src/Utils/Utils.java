package Utils;

import Utils.map.ContaMap;
import Utils.map.IContaMap;
import models.Conta;
import models.Pessoa;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    private static Utils utils = new Utils();
    private final IContaMap contaMap = new ContaMap();

    public void error(String type, String menssage) {
        String header = "------ Erro "+type+" ------";
        String text = header + "\n  " +
                menssage + "  \n" +
                "-".repeat(header.length());
        System.out.println(text);
    }

    public Conta Login() {
        System.out.println("\n------ Login ------");

        System.out.println("Informe o numero da conta ou CPF do titular: ");
        Long user = Long.parseLong(sc.nextLine().replace(".","")
                .replace("-","").replace(" ", ""));

        Conta contaAtiva = contaMap.consultaNumeroConta(user);
        if (contaAtiva == null) {
            contaAtiva = contaMap.ConsultarCpf(user);
            if (contaAtiva == null) {
                error("Usuario inexistente", "Numero da conta ou Cpf '"+ user +"' invalido");
                return null;
            }
        }

        Long senha = null;
        while (senha == null) {
            try {
                System.out.println("Senha (4 Digitos): ");
                senha = sc.nextLong();
                sc.nextLine();

                if (senha < 1000 || senha > 9999) {
                    error("Senha invalida","A senha deve ter 4 Digitos");
                    senha = null;
                }
            } catch (InputMismatchException e) {
                error("Senha invalida", "A senha deve ser apenas numeros");
                sc.nextLine();
            }
        }

        if (contaAtiva.VerificaSenha(senha)) {
            return contaAtiva;
        } else {
            error("Senha incorreta", "A senha informada é invalida");
            return null;
        }
    }

    public void cadastro() {
        System.out.println("\n------ Cadastro ------");
        System.out.println("Nome: ");
        String nome = sc.nextLine();

        Integer idade = null;
        while (idade == null) {
            try {
                System.out.println("Idade: ");
                idade = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                error("Idade invalida", "Informe apenas numeros");
                sc.nextLine();
            }
        }

        System.out.println("cpf");
        Long cpf = Long.parseLong(sc.nextLine().replace(".","")
                .replace("-","").replace(" ", ""));

        Long senha = null;
        while (senha == null) {
            try {
                System.out.println("Senha (4 Digitos): ");
                senha = sc.nextLong();
                sc.nextLine();

                if (senha < 1000 || senha > 9999) {
                    error("Senha invalida","A senha deve ter 4 Digitos");
                    senha = null;
                }
            } catch (InputMismatchException e) {
                error("Senha invalida", "A senha deve ser apenas numeros");
                sc.nextLine();
            }
        }

        Long numeroConta;
        do {
            numeroConta = 10000L + random.nextInt(90000);
        } while (utils.contaMap.consultaNumeroConta(numeroConta) != null);

        Pessoa Titular = new Pessoa(nome, idade, cpf);

        Conta conta = new Conta(Titular, numeroConta, 0.00, senha);

        contaMap.Cadastro(conta);
        System.out.println(conta);
    }
}