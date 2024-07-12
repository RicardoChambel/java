package BD;

import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;

public class Programa {

    private static final String ficheiroAlunos = "src/BD/Alunos.txt";
    private static final String ficheiroNotas = "src/BD/Notas.txt";
    private static final Path ficheiro1 = Path.of(ficheiroAlunos);
    private static final String ficheiro1Legendas = "Código/ID;Nome;Turma";
    private static final Path ficheiro2 = Path.of(ficheiroNotas);
    private static final String ficheiro2Legendas = "Código/ID;Nome;Turma;Disciplina;Nota";
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println();
        // Criar ficheiros de alunos e notas caso ainda não existam
        if(!Files.exists(ficheiro1)) {
            try {
                Files.createFile(ficheiro1);
                escreverNoFicheiro(ficheiroAlunos, ficheiro1Legendas);
                System.out.println("> Ficheiro de alunos criado");
            } catch (IOException e) {
                System.out.println("> Erro a criar ficheiro de alunos");
            }
        }else{
            System.out.println("> Ficheiro de alunos carregado");
        }
        if(!Files.exists(ficheiro2)) {
            try {
                Files.createFile(ficheiro2);
                escreverNoFicheiro(ficheiroNotas, ficheiro2Legendas);
                System.out.println("> Ficheiro de alunos criado");
            }catch (IOException e) {
                System.out.println("> Erro a criar ficheiro de notas");
            }
        }else{
            System.out.println("> Ficheiro de notas carregado");
        }



        while (true) {
            System.out.println("\n-- Menu --");
            System.out.println("[1] Adicionar Aluno");
            System.out.println("[2] Adicionar Nota");
            System.out.println("[3] Ver Notas");
            System.out.println("[4] Pesquisar Notas");
            System.out.println("[5] Filtrar Notas");
            System.out.println("[0] Sair");
            System.out.print("Escolha uma opção: ");
            try {
                int opcao = Integer.parseInt(scan.nextLine());
                switch (opcao) {
                    case 1:
                        adicionarAluno();
                        break;
                    case 2:
                        adicionarNota();
                        break;
                    case 3:
                        verNotas();
                        break;
                    case 4:
                        pesquisarNotas();
                        break;
                    case 5:
                        filtrarNotas();
                        break;
                    case 0:
                        return; // Termina o programa
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        scan.reset();
                }
            } catch (Exception e) {
                System.out.println("Opção inválida, escolha novamente");
                scan.reset();
            }

        }
    }

    private static void adicionarAluno() {
        String nome;
        String codigoAluno;
        String turma;
        String numeroAluno;

        // Pedir nome do aluno
        while (true) {
            try {
                System.out.print("(0 para cancelar) Nome do aluno: ");
                nome = scan.nextLine();
                if (!nome.isEmpty() && !nome.matches(".*\\d.*")) {
                    break;
                } else if (nome.equals("0")) {
                    return;
                } else {
                    System.out.println("> Insira um nome válido. | (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("> Nome inválido");
            }
        }

        while (true) {
            try {
                System.out.print("(0 para cancelar) Turma do aluno: ");
                turma = scan.nextLine();
                turma = turma.toUpperCase();// Meter a turma em Lower Case
                if (turma.matches("^\\d[A-Z]$")) {
                    break;
                }else if (turma.matches("^\\d{2}[A-Z]")) {
                    break;
                } else if (turma.equals("0")) {
                    return;
                } else {
                    System.out.println("> Insira uma turma válida. Formato: 0N  | (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("> Turma inválida");
            }
        }

        while (true) {
            try {
                System.out.print("(0 para cancelar) Numero do aluno: ");
                numeroAluno = scan.nextLine();
                codigoAluno = turma+numeroAluno; // Criar código aluno (Exemplo: 5D14)
                if (numeroAluno.matches("^\\d$") || numeroAluno.matches("^\\d{2}") ) {
                    if(codigoAlunoExiste(codigoAluno, numeroAluno, turma)){
                        scan.reset();
                    }else {
                        break;
                    }
                } else if (turma.equals("0")) {
                    return;
                } else {
                    System.out.println("> Insira uma turma válida. Formato: 0N  | (0 para cancelar)");
                    scan.reset();
                }
            } catch (Exception e) {
                System.out.println("> Turma inválida");
            }
        }

        codigoAluno = codigoAluno.toUpperCase(); // Meter codigo todo em Upper Case
        Aluno aluno = new Aluno(nome, codigoAluno, turma, numeroAluno); // Criação de um objeto Aluno
        String linhaAlunos = aluno.getCodigoAluno() + ";" +aluno.getNome() + ";" + aluno.getTurma();
        escreverNoFicheiro(ficheiroAlunos, linhaAlunos);// Escrever dados do Aluno no ficheiro de Alunos

        System.out.println("\n> Aluno adicionado com sucesso aos ficheiros com o código " + codigoAluno + ".");
    }

    private static boolean codigoAlunoExiste(String codigoAluno, String numeroAluno, String turma) {
        Path path = Paths.get(ficheiroAlunos);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length >= 2 && partes[0].trim().equals(codigoAluno.trim())) {
                    System.out.println("> Numero " + numeroAluno + " já atribuído na turma "+turma+" a: "+partes[1]);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
        return false;
    }

    private static void adicionarNota() {
        String nome;
        String codigoAluno;
        String disciplina;
        double nota;
        while (true) {
            // Pedir código de aluno
            while (true) {
                try {
                    System.out.print("(0 para cancelar) Código de aluno: ");
                    codigoAluno = scan.nextLine().trim();
                    codigoAluno = codigoAluno.toUpperCase();
                    if (codigoAluno.equals("0")) {
                        return;
                    } else if (codigoAluno.matches("^\\d[A-Z]\\d$") || codigoAluno.matches("^\\d[A-Z]\\d{2}$") || codigoAluno.matches("^\\d{2}[A-Z]\\d$") || codigoAluno.matches("^\\d[A-Z]\\d{2}$") ) {
                        break;
                    } else {
                        System.out.println("> Insira um código de aluno válido. Formato de código de aluno válido: 0N ou 00N (0 para cancelar)");
                    }
                } catch (Exception e) {
                    System.out.println("> Código inválido");
                }
            }

            // Pedir Nome
            while (true) {
                try {
                    System.out.print("(0 para cancelar) Nome do aluno: ");
                    nome = scan.nextLine().trim();
                    if (nome.equals("0")) {
                        return;
                    } else if (!nome.isEmpty() && !nome.matches(".*\\d.*")) {
                        break;
                    } else {
                        System.out.println("> Insira um nome válido. (0 para cancelar)");
                    }
                } catch (Exception e) {
                    System.out.println("> Nome inválido");
                }
            }

            // Pedir Disciplina
            while (true) {
                try {
                    System.out.print("Disciplina: ");
                    disciplina = scan.nextLine().trim();
                    if (!disciplina.isEmpty() && disciplina.matches("^[A-Za-záéíóúÁÉÍÓÚâêîôûÂÊÎÔÛ]+$")) {
                        break;
                    } else {
                        System.out.println("> Insira um nome de disciplina válido.");
                    }
                } catch (Exception e) {
                    System.out.println("> Disciplina inválida");
                }
            }

            // Pedir Nota
            while (true) {
                try {
                    System.out.print("Nota (0-20): ");
                    nota = scan.nextDouble();
                    if (nota >= 0 && nota <= 20) {
                        scan.nextLine(); // Limpar o buffer do scanner
                        break;
                    } else {
                        System.out.println("> Insira uma nota válida (de 0 a 20).");
                        scan.nextLine(); // Limpar o buffer do scanner
                    }
                } catch (Exception e) {
                    System.out.println("> Nota inválida");
                    scan.nextLine(); // Limpar o buffer do scanner
                }
            }

            // Verificar se o aluno com o código existe
            if (codigoAlunoExisteNota(nome, codigoAluno)) {
                String turma = getTurmaAluno(nome, codigoAluno);
                if (turma != null) {
                    Nota notaObj = new Nota(nome, codigoAluno, turma, disciplina, nota); // Criação de um objeto Nota
                    String linhaNotas = notaObj.getCodigoAluno()+"; "+notaObj.getNome() + "; " + notaObj.getTurma() + "; " + notaObj.getDisciplina() + "; " + notaObj.getNota();
                    escreverNoFicheiro(ficheiroNotas, linhaNotas);
                    System.out.println("\n> Nota adicionada com sucesso ao ficheiro \"Notas.txt\".\n");
                    break;
                } else {
                    System.out.println("> Erro ao obter a turma do aluno.");
                }
            } else {
                System.out.println("> Aluno não encontrado com o nome e código fornecidos.");
            }
        }

    }

    private static boolean codigoAlunoExisteNota(String nome, String codigoAluno) {
        Path path = Paths.get(ficheiroAlunos);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length >= 2 && partes[0].trim().equals(codigoAluno.trim()) && partes[1].trim().equals(nome.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("> Erro ao ler o ficheiro: " + e.getMessage());
        }
        return false;
    }

    private static String getTurmaAluno(String nome, String codigoAluno) {
        Path path = Paths.get(ficheiroAlunos);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length >= 3 && partes[0].trim().equals(codigoAluno.trim()) && partes[1].trim().equals(nome.trim())) {
                    return partes[2].trim(); // Retorna a turma
                }
            }
        } catch (IOException e) {
            System.out.println("> Erro ao ler o ficheiro: " + e.getMessage());
        }
        return null; // Caso não encontre a turma, retorna null
    }

    private static void escreverNoFicheiro(String nomeFicheiro, String linha) {
        Path path = Paths.get(nomeFicheiro);
        try {
            Files.write(path, (linha + "\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.out.println("> Erro ao escrever no ficheiro " + nomeFicheiro + ": " + e.getMessage());
        }
    }

    private static void verNotas() {
        Path path = Paths.get(ficheiroNotas);
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) {
                System.out.println("> Nenhuma nota encontrada.");
            } else {
                System.out.println("\n-- Lista de Notas --");
                for (String linha : linhas) {
                    System.out.println(linha);
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("> Erro ao ler o ficheiro de notas: " + e.getMessage());
        }
    }

    private static void pesquisarNotas() {
        Path path = Paths.get(ficheiroNotas);
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) {
                System.out.println("> Nenhuma nota encontrada.");
                return;
            }

            // Ler a primeira linha separadamente
            String primeiraLinha = linhas.getFirst();

            // Ler as restantes linhas
            List<String> linhasRestantes = linhas.subList(1, linhas.size());

            System.out.println("\n-- Pesquisar Notas --");
            System.out.println("[1] Organizar por Nome");
            System.out.println("[2] Organizar por Disciplina");
            System.out.println("[3] Organizar por Turma");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scan.nextLine());

            List<String[]> notas = linhasRestantes.stream()
                    .map(linha -> linha.split(";\\s*"))
                    .collect(Collectors.toList());

            switch (opcao) {
                case 1:
                    notas.sort(Comparator.comparing(n -> n[1])); // Nome
                    break;
                case 2:
                    notas.sort(Comparator.comparing(n -> n[3])); // Disciplina
                    break;
                case 3:
                    notas.sort(Comparator.comparing(n -> n[2])); // Turma
                    break;
                default:
                    System.out.println("> Opção inválida. Tente novamente.");
                    return;
            }

            System.out.println("\n-- Notas --");
            System.out.println(primeiraLinha); // Exibir a primeira linha

            for (String[] nota : notas) {
                System.out.println(String.join("; ", nota));
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("> Erro ao ler o ficheiro de notas: " + e.getMessage());
        }
    }

    private static void filtrarNotas() {
        Path path = Paths.get(ficheiroNotas);
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) {
                System.out.println("> Nenhuma nota encontrada.");
                return;
            }

            // Ler a primeira linha separadamente
            String primeiraLinha = linhas.get(0);

            // Ler as restantes linhas
            List<String> linhasRestantes = linhas.subList(1, linhas.size());

            System.out.println("\n-- Filtrar Notas --");
            System.out.println("[1] Filtrar por Disciplina");
            System.out.println("[2] Filtrar por Turma");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scan.nextLine());

            List<String[]> notas = linhasRestantes.stream()
                    .map(linha -> linha.split(";\\s*"))
                    .collect(Collectors.toList());

            switch (opcao) {
                case 1:
                    System.out.print("Disciplina: ");
                    String disciplina = scan.nextLine().trim();

                    List<String[]> notasFiltradasDisciplina = notas.stream()
                            .filter(n -> n[3].equalsIgnoreCase(disciplina))
                            .collect(Collectors.toList());

                    if (notasFiltradasDisciplina.isEmpty()) {
                        System.out.println("> Nenhuma nota encontrada para a disciplina " + disciplina);
                    } else {
                        System.out.println("\n-- Notas para Disciplina " + disciplina + " --");
                        System.out.println(primeiraLinha); // Exibir a primeira linha
                        for (String[] nota : notasFiltradasDisciplina) {
                            System.out.println(String.join("; ", nota));
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Turma: ");
                    String turma = scan.nextLine().trim();

                    List<String[]> notasFiltradasTurma = notas.stream()
                            .filter(n -> n[2].equalsIgnoreCase(turma))
                            .collect(Collectors.toList());

                    if (notasFiltradasTurma.isEmpty()) {
                        System.out.println("> Nenhuma nota encontrada da turma " + turma);
                    } else {
                        System.out.println("\n-- Notas da Turma: " + turma + " --");
                        System.out.println(primeiraLinha); // Exibir a primeira linha
                        for (String[] nota : notasFiltradasTurma) {
                            System.out.println(String.join("; ", nota));
                        }
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("> Opção inválida.");
            }
        } catch (IOException e) {
            System.out.println("> Erro ao ler o ficheiro de notas: " + e.getMessage());
        }
    }
}
