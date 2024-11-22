package Utils;

import Utils.map.ContaMap;
import Utils.map.IContaMap;
import models.Conta;
import models.Pessoa;
import models.Transacao;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
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

                if (valor <= 0) {
                    error("Valor invalido","Informe um valor positivo");
                    valor = null;
                }
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
        String userString = sc.nextLine().replace(".","")
                .replace("-","").replace(" ", "");

        Long user = null;
        while (user == null) {
            try {
                user = Long.parseLong(userString);

            } catch (NumberFormatException e) {
                error("cpf invalido", "Informe um cpf ou numero da conta valido");
                userString = sc.nextLine();
            }
        }

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
            contaAtiva.getTitular().AtualizaIdade();
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

        LocalDate dataNascimento = null;
        Integer idade = null;
        while (dataNascimento == null) {
            try {
                System.out.println("Data de nascimento (dd/MM/yyyy): ");
                String dataInput = sc.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataNascimento = LocalDate.parse(dataInput, formatter);

                idade = Period.between(dataNascimento, LocalDate.now()).getYears();

                if (idade < 18) {
                    error("Idade inválida", "Para criar uma conta deve ser maior de idade");
                    dataNascimento = null;
                }
            } catch (DateTimeParseException e) {
                error("Data inválida", "Informe a data no formato dd/MM/yyyy.");
            }
        }

        System.out.println("cpf");
        Long cpf = Long.parseLong(sc.nextLine().replace(".","")
                .replace("-","").replace(" ", ""));

        Long senha = getSenha("Senha (4 Digitos): ");

        Long numeroConta;
        do {
            numeroConta = 10000L + random.nextInt(90000);
        } while (this.contaMap.consultaNumeroConta(numeroConta) != null);

        Pessoa Titular = new Pessoa(nome, idade, cpf, dataNascimento, numeroConta);

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

    public void test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNascimento = LocalDate.parse("01/01/2000", formatter);
        Pessoa Titular = new Pessoa("Test", 24, 99999999999L, dataNascimento, 11111L);
        Conta conta = new Conta(Titular, 11111L, 400.00, 1234L);

        Transacao transacao = new Transacao(new Date("08/07/2024"), 1000.00, false, false, 0.00,0.00);
        Transacao transacao1 = new Transacao(new Date("09/07/2024"), 500.00, true, false, 1000.00,0.00);
        Transacao transacao1_1 = new Transacao(new Date("09/07/2024"), 500.00, false, true, 500.00,0.00);
        Transacao transacao3 = new Transacao(new Date("10/06/2024"), 100.00, false, false, 500.00,500.00);
        Transacao transacao3_1 = new Transacao(new Date("10/06/2024"), 100.00, true, true, 600.00,500.00);
        Transacao transacao4 = new Transacao(new Date("12/10/2024"), 300.00, true, false, 600.00,400.00);
        Transacao transacao5 = new Transacao(new Date("12/10/2024"), 300.00, true, false, 300.00,400.00);
        Transacao transacao6 = new Transacao(new Date("10/11/2024"), 400.00, false, false, 0.00,400.00);
        Transacao transacao6_1 = new Transacao(new Date("10/11/2024"), 400.00, true, true, 400.00,400.00);

        conta.getExtrato().addToExtrato(transacao);
        conta.getExtrato().addToExtrato(transacao1);
        conta.getExtrato().addToExtrato(transacao1_1);
        conta.getExtrato().addToExtrato(transacao3);
        conta.getExtrato().addToExtrato(transacao3_1);
        conta.getExtrato().addToExtrato(transacao4);
        conta.getExtrato().addToExtrato(transacao5);
        conta.getExtrato().addToExtrato(transacao6);
        conta.getExtrato().addToExtrato(transacao6_1);

        contaMap.Cadastro(conta);
    }
}
