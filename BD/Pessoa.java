package BD;

public class Pessoa {
    protected String nome;
    protected String codigoAluno;

    public Pessoa(String nome, String codigoAluno) {
        this.nome = nome;
        this.codigoAluno = codigoAluno;
    }

    public String getNome() {
        return nome;
    }
}
