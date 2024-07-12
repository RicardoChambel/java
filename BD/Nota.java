package BD;

public class Nota extends Registo {
    private String codigoAluno;
    private String turma;

    public Nota(String nome, String codigoAluno, String turma, String disciplina, double nota) {
        super(nome, disciplina, nota, codigoAluno);
        this.codigoAluno = codigoAluno;
        this.turma = turma;
    }

    public String getCodigoAluno() {
        return codigoAluno;
    }

    public String getTurma() {
        return turma;
    }
}
