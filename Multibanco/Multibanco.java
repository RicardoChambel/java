package Multibanco;


import java.nio.file.*;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Multibanco {

    public static void main() {
        Scanner scan = new Scanner(System.in);

        String numeroDeConta;
        String pin;

        String numeroDeContaREGEX = "\\d{8}-\\d"; // Formato 8 dígitos com o último separado por hífen \\d{8} = oito dígitos | \\d = um dígito
        Pattern numeroDeContaREGEXPattern = Pattern.compile(numeroDeContaREGEX);

        String pinREGEX = "\\d{4}"; // Formato de 4 dígitos
        Pattern pinREGEXPattern = Pattern.compile(pinREGEX);


        // "LOGIN" ------------------------
        // Pedir número da conta
        Conta conta = new Conta();
        System.out.println("-- Login na Conta --");
        while (true) {
            System.out.println("Número de conta: ");
            numeroDeConta = scan.nextLine().trim();
            if (numeroDeContaREGEXPattern.matcher(numeroDeConta).matches() && !numeroDeConta.startsWith("00000000")) {
                conta.setNumeroDeConta(numeroDeConta);
                conta.setNome("Utilizador");
                conta.setSaldo(1000);
                break;
            } else {
                System.out.println("Número de conta inválido. Formato do número de uma conta válido: 00000000-0.\n");
            }
        }
        // Pedir o pin da conta
        while (true) {
            System.out.println("PIN: ");
            pin = scan.nextLine().trim();
            if (pinREGEXPattern.matcher(pin).matches() && !pin.startsWith("0000")) {
                conta.setPin(pin);
                break;
            } else {
                System.out.println("PIN inválido. Formato de um PIN válido: 0000.");
            }
        }
        conta.setFicheiro(); // Criar ficheiro da conta
        menu(conta);
        System.exit(0);
    }

    public static void menu(Conta conta){
        Scanner scan = new Scanner(System.in);
        while (true) {
            Integer resposta;
            System.out.println("\n-- Multibanco --");
            System.out.println("[1] • Levantar");
            System.out.println("[2] • Depositar");
            System.out.println("[3] • Dados Da Conta");
            System.out.println("[4] • Transações");
            System.out.println("[0] • Sair");
            try {
                resposta = Integer.parseInt(scan.nextLine().trim());
                if (resposta < 0 || resposta > 4) {
                    System.out.println("Opção inválida, escolha novamente");
                    scan.reset();
                } else {
                    if (resposta == 1) {
                        levantar(conta, scan);
                    } else if (resposta == 2) {
                        depositar(conta, scan);
                    } else if (resposta == 3) {
                        dadosDaConta(conta, scan);
                    } else if (resposta==4) {
                        transacoes(conta, scan);
                    } else { // Caso a resposta seja 0
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println("Opção inválida, escolha novamente");
                scan.reset();
            }
        }
    }

    public static void levantar(Conta conta, Scanner scan ) {
        while (true) {
            System.out.println("\n-- Multibanco --");
            System.out.println("(Inserir 0 para voltar ao menu)");
            System.out.println("Valor a levantar:");
            try {
                double valor = scan.nextDouble();
                if (valor > 0 && valor <= conta.getSaldo()) {
                    conta.setSaldo(conta.getSaldo() - valor);
                    System.out.println("Levantado com sucesso. Novo saldo: " + conta.getSaldo()+"€");
                    conta.addTransacao("-",valor); // Adicionar transação ao ficheiro do utilizador
                    break;
                } else if(valor < 0){
                    System.out.println("Valor Inválido");
                    scan.reset();
                } else if (valor==0) {
                    break; // Ao inserir 0 volta para o menu
                } else{
                    System.out.println("Saldo insuficiente.");
                    scan.reset();
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
                scan.reset();
            }
        }
    }
    
    public static void depositar(Conta conta, Scanner scan) {
        while (true) {
            System.out.println("\n-- Multibanco --");
            System.out.println("(Inserir 0 para voltar ao menu)");
            System.out.println("Valor a depositar:");
            try {
                double valor = scan.nextDouble();
                if (valor > 0 ) {
                    valor = Math.round(valor*100.0)/100.0; // Arredondar o valor inserido a 2 casa decimais
                    conta.setSaldo(conta.getSaldo() + valor);
                    System.out.println("Depositado com sucesso sucedido. Novo saldo: " + conta.getSaldo()+"€");
                    conta.addTransacao("+",valor); // Adicionar transação ao ficheiro do utilizador
                    break;
                } else if(valor < 0){
                    System.out.println("Valor Inválido");
                    scan.reset();
                } else if (valor==0) {
                    break;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
                scan.reset();
            }
        }
    }

    public static void dadosDaConta(Conta conta, Scanner scan) {
        while (true) {
            System.out.println("\n-- Multibanco --");
            System.out.println("• Titular: "+conta.getNome());
            System.out.println("• Numero de Conta: "+conta.getNumeroDeConta());
            System.out.println("• Saldo: "+conta.getSaldo()+"€");
            System.out.println("[0] • Voltar");
            try {
                int opcao;
                opcao = scan.nextInt();
                if (opcao==0) {
                    menu(conta);
                }
                else {
                    System.out.println("Por favor escolha uma opção válida.");
                }
            }catch (Exception e) {
                System.out.println("Valor inválido");
                scan.reset();
            }
        }
    }

    public static void transacoes(Conta conta, Scanner scan) {
        while (true) {
            int resposta;
            String numeroDeConta = conta.getNumeroDeConta();
            Path path = Paths.get(numeroDeConta+".log");
            try{
                List<String> linhas = Files.readAllLines(path);
                int numeroDeLinha = 0;
                for (String linha : linhas) {
                    if (numeroDeLinha==0){
                        System.out.println(linha);
                    }else {
                        System.out.println(numeroDeLinha+" | "+linha);
                    }
                    numeroDeLinha++;
                }
                try {
                    System.out.println("[0] • Voltar");
                    resposta = Integer.parseInt(scan.nextLine());
                    if (resposta == 0) {
                        break;
                    } else{
                        System.out.println("Escolha uma opção válida");
                        scan.reset();
                    }
                }catch (Exception e) {
                    System.out.println("Valor inválido");
                    scan.reset();
                }
            }catch (IOException e){
                System.out.println("Erro ao escrever ficheiro!");
            }
        }
    }
}
