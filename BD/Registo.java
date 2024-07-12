package BD;

public class Registo extends Pessoa {
    protected String disciplina;
    protected double nota;

    public Registo(String nome, String disciplina, double nota, String codigoAluno) {
        super(nome, codigoAluno);
        this.disciplina = disciplina;
        this.nota = nota;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public double getNota() {
        return nota;
    }
}
