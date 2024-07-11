package BD;

import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.stream.Collectors;

public class Programa {

    private static final String ficheiroAlunos = "Alunos.txt";
    private static final String ficheiroNotas = "Notas.txt";
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("-- Menu --");
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
                        break;
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

        // Captura das informações do aluno
        while (true) {
            try {
                System.out.print("(0 para cancelar) Nome do aluno: ");
                nome = scan.nextLine();
                if (!nome.isEmpty() && !nome.matches(".*\\d.*")) {
                    break;
                } else if (nome.equals("0")) {
                    main(null);
                } else {
                    System.out.println("Insira um nome válido. | (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("Nome inválido");
            }
        }

        while (true) {
            try {
                System.out.print("(0 para cancelar) Código de aluno: ");
                codigoAluno = scan.nextLine();
                if (codigoAlunoExiste(codigoAluno)) {
                    System.out.println("Código de aluno já existe. Por favor, insira outro código. | (0 para cancelar)");
                }else if (!codigoAluno.isEmpty() && codigoAluno.matches("^[A-Za-z]\\d{4}$")) {
                    break;
                }else if (codigoAluno.equals("0")){
                    main(null);
                }else {
                    System.out.println("Insira um código de aluno válido. Formato de código de aluno válido: N0000 | (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("Código inválido");
            }
        }

        while (true) {
            try {
                System.out.print("(0 para cancelar) Turma do aluno: ");
                turma = scan.nextLine();
                if (!turma.isEmpty() && turma.matches("^\\d[A-Z]$")) {
                    break;
                } else if (turma.equals("0")) {
                    main(null);
                } else {
                    System.out.println("Insira uma turma válida. Formato: 0N  | (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("Turma inválida");
            }
        }

        String linhaAlunos = nome + ";" + codigoAluno + ";" + turma; // Criar a linha de texto com os dados do aluno
        escreverNoFicheiro(ficheiroAlunos, linhaAlunos);// Escrever dados do Aluno no ficheiro de Alunos

        System.out.println("Aluno adicionado com sucesso aos ficheiros.");
    }

    private static boolean codigoAlunoExiste(String codigoAluno) {
        Path path = Paths.get(ficheiroAlunos);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length >= 2 && partes[1].trim().equals(codigoAluno.trim())) {
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

        // Pedir Nome
        while (true) {
            try {
                System.out.print("(0 para cancelar) Nome do aluno: ");
                nome = scan.nextLine().trim();
                if (nome.equals("0")) {
                    main(null);
                    return;
                } else if (!nome.isEmpty() && !nome.matches(".*\\d.*")) {
                    break;
                } else {
                    System.out.println("Insira um nome válido. (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("Nome inválido");
            }
        }

        // Pedir código de aluno
        while (true) {
            try {
                System.out.print("(0 para cancelar) Código de aluno: ");
                codigoAluno = scan.nextLine().trim();
                if (codigoAluno.equals("0")) {
                    main(null);
                    return;
                } else if (!codigoAluno.isEmpty() && codigoAluno.matches("^[A-Za-z]\\d{4}$")) {
                    break;
                } else {
                    System.out.println("Insira um código de aluno válido. Formato de código de aluno válido: N0000 (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("Código inválido");
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
                    System.out.println("Insira um nome de disciplina válido.");
                }
            } catch (Exception e) {
                System.out.println("Disciplina inválida");
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
                    System.out.println("Insira uma nota válida (de 0 a 20).");
                    scan.nextLine(); // Limpar o buffer do scanner
                }
            } catch (Exception e) {
                System.out.println("Nota inválida");
                scan.nextLine(); // Limpar o buffer do scanner
            }
        }

        // Verificar se o aluno com o código existe
        if (codigoAlunoExisteNota(nome, codigoAluno)) {
            String turma = obterTurmaAluno(nome, codigoAluno);
            if (turma != null) {
                String linhaNotas = nome + "; " + turma + "; " + disciplina + "; " + nota;
                escreverNoFicheiro(ficheiroNotas, linhaNotas);
                System.out.println("Nota adicionada com sucesso ao ficheiro \"Notas.txt\".\n");
            } else {
                System.out.println("Erro ao obter a turma do aluno.");
            }
        } else {
            System.out.println("Aluno não encontrado com o nome e código fornecidos.");
        }
    }

    private static boolean codigoAlunoExisteNota(String nome, String codigoAluno) {
        Path path = Paths.get(ficheiroAlunos);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length >= 2 && partes[0].trim().equals(nome.trim()) && partes[1].trim().equals(codigoAluno.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
        return false;
    }

    private static String obterTurmaAluno(String nome, String codigoAluno) {
        Path path = Paths.get(ficheiroAlunos);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length >= 3 && partes[0].trim().equals(nome.trim()) && partes[1].trim().equals(codigoAluno.trim())) {
                    return partes[2].trim(); // Retorna a turma
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
        return null; // Caso não encontre a turma, retorna null
    }

    private static void escreverNoFicheiro(String nomeFicheiro, String linha) {
        Path path = Paths.get(nomeFicheiro);
        try {
            Files.write(path, (linha + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no ficheiro " + nomeFicheiro + ": " + e.getMessage());
        }
    }

    private static void verNotas() {
        Path path = Paths.get(ficheiroNotas);
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) {
                System.out.println("Nenhuma nota encontrada.");
            } else {
                System.out.println("\n-- Lista de Notas --");
                for (String linha : linhas) {
                    System.out.println(linha);
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de notas: " + e.getMessage());
        }
    }

    private static void pesquisarNotas() {
        Path path = Paths.get(ficheiroNotas);
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) {
                System.out.println("Nenhuma nota encontrada.");
                return;
            }

            System.out.println("\n-- Pesquisar Notas --");
            System.out.println("[1] Organizar por Nome");
            System.out.println("[2] Organizar por Disciplina");
            System.out.println("[3] Organizar por Turma");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scan.nextLine());

            List<String[]> notas = linhas.stream()
                    .map(linha -> linha.split(";\\s*"))
                    .collect(Collectors.toList());

            switch (opcao) {
                case 1:
                    notas.sort(Comparator.comparing(n -> n[0])); // Nome
                    break;
                case 2:
                    notas.sort(Comparator.comparing(n -> n[2])); // Disciplina
                    break;
                case 3:
                    notas.sort(Comparator.comparing(n -> n[1])); // Turma
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    return;
            }

            System.out.println("\n-- Notas --");
            for (String[] nota : notas) {
                System.out.println(String.join("; ", nota));
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de notas: " + e.getMessage());
        }
    }

    private static void filtrarNotas() {
        Path path = Paths.get(ficheiroNotas);
        try {
            List<String> linhas = Files.readAllLines(path);
            if (linhas.isEmpty()) {
                System.out.println("Nenhuma nota encontrada.");
                return;
            }

            System.out.println("\n-- Filtrar Notas --");
            System.out.println("[1] Filtrar por Disciplina");
            System.out.println("[2] Filtrar por Turma");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(scan.nextLine());

            List<String[]> notas = linhas.stream()
                    .map(linha -> linha.split(";\\s*"))
                    .toList();

            switch (opcao) {
                case 1:
                    System.out.print("Disciplina: ");
                    String disciplina = scan.nextLine().trim();

                    List<String[]> notasFiltradasDisciplina = notas.stream()
                            .filter(n -> n[2].equalsIgnoreCase(disciplina))
                            .toList();

                    if (notasFiltradasDisciplina.isEmpty()) {
                        System.out.println("Nenhuma nota encontrada para a disciplina " + disciplina);
                    } else {
                        System.out.println("\n-- Notas para Disciplina " + disciplina + " --");
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
                            .filter(n -> n[1].equalsIgnoreCase(turma))
                            .toList();

                    if (notasFiltradasTurma.isEmpty()) {
                        System.out.println("Nenhuma nota encontrada da turma " + turma);
                    } else {
                        System.out.println("-- Notas da Turma: " + turma + " --");
                        for (String[] nota : notasFiltradasTurma) {
                            System.out.println(String.join("; ", nota));
                        }
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de notas: " + e.getMessage());
        }
    }

}