package BD;

public class Aluno extends Pessoa {
    private String turma;
    private String numeroAluno;

    public Aluno(String nome, String numeroAluno, String turma) {
        super(nome, numeroAluno);
        this.turma = turma;
        this.numeroAluno = numeroAluno;
    }

    public String getNumeroAluno() {
        return numeroAluno;
    }

    public String getTurma() {
        return turma;
    }

    @Override
    public String toString() {
        return turma + ";" + numeroAluno + ";" + getNome();
    }
}
