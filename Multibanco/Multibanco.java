package Multibanco;

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

        menu(conta);
    }

    public static void menu(Conta conta){
        Scanner scan = new Scanner(System.in);
        while (true) {
            Integer resposta;
            System.out.println("\n-- Multibanco --");
            System.out.println("[1] • Levantar");
            System.out.println("[2] • Depositar");
            System.out.println("[3] • Dados Da Conta");
            System.out.println("[0] • Sair");
            try {
                resposta = Integer.parseInt(scan.nextLine().trim());
                if (resposta < 0 || resposta > 3) {
                    System.out.println("Opção inválida, escolha novamente");
                    scan.reset();
                } else {
                    if (resposta == 1) {
                        levantar(conta);
                    } else if (resposta == 2) {
                        depositar(conta);
                    } else if (resposta == 3) {
                        dadosDaConta(conta);
                    }else {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Opção inválida, escolha novamente");
                scan.reset();
            }
        }
    }

    public static void levantar(Conta conta) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n-- Multibanco --");
            System.out.println("(Inserir 0 para voltar ao menu)");
            System.out.println("Valor a levantar:");
            try {
                double valor = Double.parseDouble(scan.nextLine().trim());
                if (valor > 0 && valor <= conta.getSaldo()) {
                    conta.setSaldo(conta.getSaldo() - (int) valor);
                    System.out.println("Levantado com sucesso. Novo saldo: " + conta.getSaldo()+"€");
                    break;
                } else if(valor < 0){
                    System.out.println("Valor Inválido");
                    scan.reset();
                } else if (valor==0) {
                    break;
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
    
    public static void depositar(Conta conta) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n-- Multibanco --");
            System.out.println("(Inserir 0 para voltar ao menu)");
            System.out.println("Valor a depositar:");
            try {
                double valor = Double.parseDouble(scan.nextLine().trim());
                if (valor > 0 ) {
                    conta.setSaldo(conta.getSaldo() + (int) valor);
                    System.out.println("Depositado com sucesso sucedido. Novo saldo: " + conta.getSaldo()+"€");
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

    public static void dadosDaConta(Conta conta) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n-- Multibanco --");
            System.out.println("• Titular: "+conta.getNome());
            System.out.println("• Numero de Conta: "+conta.getNumeroDeConta());
            System.out.println("• Saldo: "+conta.getSaldo()+"€");
            System.out.println("[0] • Voltar");
            try {
                Integer opcao;
                opcao = Integer.parseInt(scan.nextLine().trim());
                if (opcao.equals(0)) {
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
}
