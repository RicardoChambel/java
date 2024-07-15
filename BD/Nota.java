package BD;

public class Nota extends Registo {
    private String numeroAluno;
    private String turma;

    public Nota(String nome, String numeroAluno, String turma, String disciplina, double nota) {
        super(nome, disciplina, nota, numeroAluno);
        this.numeroAluno = numeroAluno;
        this.turma = turma;
    }

    public String getNumeroAluno() {
        return numeroAluno;
    }

    public String getTurma() {
        return turma;
    }
}
