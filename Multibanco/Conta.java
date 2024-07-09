package Multibanco;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Conta {
    private String nome;
    private String numeroDeConta;
    private double saldo;
    private String pin;

    public Boolean hasFicheiro(Path path){ // Verificar se existe ficheiro do utilizador
        return Files.exists(path);
    }

    public void setFicheiro(){ // Criar ficheiro para utilizador
        String numeroDeConta = getNumeroDeConta();
        double saldo = getSaldo();

        Path path = Paths.get(numeroDeConta+".log");
        hasFicheiro(path); // Verificar se o Utilizador tem um ficheiro

        while (true) {
            if (!hasFicheiro(path)) { // Caso não tenha, criar um ficheiro
                List<String> lines = List.of("\n-- Transações --");
                try {
                    Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    System.out.println("Ficheiro de logs criado");
                    break;
                } catch (IOException e) {
                    System.out.println("Erro ao escrever ficheiro!");
                }
            } else { // Caso tenha, apagar, para criar um novo
                try{
                    Files.delete(path);
                }catch (IOException e){
                    System.out.println("Erro ao apagar ficheiro!");
                }
            }
        }
    }

    public void addTransacao(String simbolo, double transacao){
        String numeroDeConta = getNumeroDeConta();
        double saldo = getSaldo();
        Path path = Paths.get(numeroDeConta+".log");
        List<String> info = List.of("Transação: "+simbolo+transacao+"€");
        try{
            Files.write(path, info, StandardOpenOption.APPEND);
            System.out.println("Guardado no ficheiro de logs");
        }catch (IOException e){
            System.out.println("Erro ao escrever ficheiro!");
        }

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroDeConta() {
        return numeroDeConta;
    }

    public void setNumeroDeConta(String numeroDeConta) {
        this.numeroDeConta = numeroDeConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
