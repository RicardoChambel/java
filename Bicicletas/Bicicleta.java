package Bicicletas;

public class Bicicleta {

    private String nome;
    private String mudancas;
    private String roda;
    private String id; // Esta variável é um ID para cada bicicleta da sua categoria (elétrica e não elétrica)
    private String tipo;

    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMudancas() {
        return mudancas;
    }

    public void setMudancas(String mudancas) {
        this.mudancas = mudancas;
    }

    public String getRoda() {
        return roda;
    }

    public void setRoda(String roda) {
        this.roda = roda;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class BicicletaEletrica extends Bicicleta {

    private boolean motor;

    public boolean hasMotor() {
        return motor;
    }

    public void setMotor(boolean motor) {
        this.motor = motor;
    }
}