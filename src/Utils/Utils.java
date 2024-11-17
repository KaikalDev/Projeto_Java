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
    public final IContaMap contaMap = new ContaMap();

    public void error(String type, String menssage) {
        String header = "------ Erro "+type+" ------";
        String text = header + "\n  " +
                menssage + "  \n" +
                "-".repeat(header.length());
        System.out.println(text);
    }

    public Long getSenha(String text) {
        Long senha = null;
        while (senha == null) {
            try {
                System.out.println(text);
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

        return senha;
    }

    public Double getvalor(String text) {
        Double valor = null;
        while (valor == null) {
            try {
                System.out.print(text);
                valor = sc.nextDouble();
                sc.nextLine();
            } catch (InputMismatchException e) {
                error("Valor invalido", "Informe o valor no formato (0.00)");
                sc.nextLine();
            }
        }
        return valor;
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

        Long senha = getSenha("Senha (4 Digitos): ");

        Long numeroConta;
        do {
            numeroConta = 10000L + random.nextInt(90000);
        } while (utils.contaMap.consultaNumeroConta(numeroConta) != null);

        Pessoa Titular = new Pessoa(nome, idade, cpf);

        Conta conta = new Conta(Titular, numeroConta, 0.00, senha);

        contaMap.Cadastro(conta);
        System.out.println(conta);
    }

    public int displayIfos(Conta contaAtiva) {
        String header = "------ Conta Nº " + contaAtiva.getNumeroConta() + " ------";
        String display = header + "\n"
                + "Olá " + contaAtiva.getTitular().getNome() + "\n"
                + "Saldo da Conta Corrente: " + contaAtiva.getSaldo() + "\n"
                + "Saldo sa Conta Poupança: " + contaAtiva.getCP().getSaldo() + "\n"
                + "-".repeat(header.length());
        String Actions = "Ações: \n"
                + "1 - Depósito na Conta Corrente \n"
                + "2 - Saque na Conta Corrente \n"
                + "3 - Depósito na Poupança \n"
                + "4 - Saque na Poupança \n"
                + "5 - Simular Rendimentos \n"
                + "6 - Consultar Extrato \n"
                + "7 - Alterar senha \n"
                + "0 - Sair \n"
                + "-".repeat(header.length());

        System.out.println(display);
        int resposta;
        do {
            System.out.println(Actions);
            resposta = sc.nextInt();
            sc.nextLine();
        } while (resposta < 0 || resposta > 7);

        return resposta;
    }
}
