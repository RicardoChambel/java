package BD;

import java.nio.file.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.io.IOException;

public class Programa {

    private static String ficheiroAlunos = "src/BD/Alunos.txt";
    private static String ficheiroNotas = "src/BD/Notas.txt";
    private static Path ficheiro1 = Path.of(ficheiroAlunos);
    private static Path ficheiro2 = Path.of(ficheiroNotas);
    private static Scanner scan = new Scanner(System.in);

    private static List<String> listaAlunos = new ArrayList<>();
    private static List<String> listaNotas = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println();

        carregarDados(); // Carregar dados dos ficheiros para as listas

        if (!Files.exists(ficheiro1)) { // Criar ficheiros de alunos e notas caso ainda não existam
            try {
                Files.createFile(ficheiro1);

                String ficheiro1Legendas = "Turma;Numero;Nome";
                listaAlunos.add(ficheiro1Legendas);
                String ficheiro1Legendas2 = "0N;0;Nome";
                listaAlunos.add(ficheiro1Legendas2);

                System.out.println("> Ficheiro de alunos criado");
            } catch (IOException e) {
                System.out.println("> Erro a criar ficheiro de alunos");
            }
        } else {
            System.out.println("> Ficheiro de alunos carregado");
        }
        if (!Files.exists(ficheiro2)) {
            try {
                Files.createFile(ficheiro2);

                String ficheiro2Legendas = "Turma;Numero;Nome;Disciplina;Nota";
                listaNotas.add(ficheiro2Legendas);
                String ficheiro2Legendas2 = "0N;0;Nome;Disciplina;0.0";
                listaNotas.add(ficheiro2Legendas2);

                System.out.println("> Ficheiro de notas criado");
            } catch (IOException e) {
                System.out.println("> Erro a criar ficheiro de notas");
            }
        } else {
            System.out.println("> Ficheiro de notas carregado");
        }

        while (true) {
            System.out.println("\n-- Menu --");
            System.out.println("[1] Adicionar Aluno");
            System.out.println("[2] Ver Alunos");
            System.out.println("[3] Adicionar Nota");
            System.out.println("[4] Ver Notas");
            System.out.println("[5] Pesquisar Notas");
            System.out.println("[6] Filtrar Notas (Não funciona)");
            System.out.println("[0] Sair e Guardar");
            System.out.print("Escolha uma opção: ");
            try {
                int opcao = Integer.parseInt(scan.nextLine());
                switch (opcao) {
                    case 1:
                        adicionarAluno();
                        break;
                    case 2:
                        verAlunos();
                        break;
                    case 3:
                        adicionarNota();
                        break;
                    case 4:
                        verNotas();
                        break;
                    case 5:
                        pesquisarNotas();
                        break;
                    case 6:
                        //filtrarNotas();
                        System.out.println("Opção inválida. Tente novamente.");
                        scan.reset();
                        break;
                    case 0:
                        salvarDados();
                        return;
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

    private static void carregarDados() {
        try {
            if (Files.exists(ficheiro1)) {
                listaAlunos = Files.readAllLines(ficheiro1);
            }
            if (Files.exists(ficheiro2)) {
                listaNotas = Files.readAllLines(ficheiro2);
            }
        } catch (IOException e) {
            System.out.println("> Erro ao carregar os dados: " + e.getMessage());
        }
    }

    private static void salvarDados() {
        try {
            Files.write(ficheiro1, listaAlunos, StandardOpenOption.TRUNCATE_EXISTING); //
            Files.write(ficheiro2, listaNotas, StandardOpenOption.TRUNCATE_EXISTING); // Truncate Existing para escrever em cima do ficheiro existente
        } catch (IOException e) {
            System.out.println("> Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private static void adicionarAluno() {
        String nome;
        String turma;
        String numeroAluno;

        while (true) {
            try {
                System.out.print("(0 para cancelar) Turma do aluno: ");
                turma = scan.nextLine();
                turma = turma.toUpperCase(); // Converter para maiúsculas
                if (turma.matches("^\\d[A-Z]$") || turma.matches("^\\d{2}[A-Z]$")) {
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
                System.out.print("(0 para cancelar) Número do aluno: ");
                numeroAluno = scan.nextLine();
                if (numeroAluno.matches("^\\d{1,2}$")) {
                    if (AlunoExiste(numeroAluno, turma)) {
                        scan.reset();
                    } else {
                        break;
                    }
                } else if (numeroAluno.equals("0")) {
                    return;
                } else {
                    System.out.println("> Insira um número de aluno válido. (0 para cancelar)");
                    scan.reset();
                }
            } catch (Exception e) {
                System.out.println("> Número de aluno inválido");
            }
        }

        while (true) {
            try {
                System.out.print("(0 para cancelar) Nome do aluno: ");
                nome = scan.nextLine();
                if (!nome.isEmpty() && !nome.matches(".*\\d.*")) {
                    break;
                } else if (nome.equals("0")) {
                    return;
                } else {
                    System.out.println("> Insira um nome válido. (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("> Nome inválido");
            }
        }

        Aluno aluno = new Aluno(nome, numeroAluno, turma); // Criar um novo aluno
        String linhaAlunos = aluno.getTurma() + ";" + aluno.getNumeroAluno() + ";" + aluno.getNome();
        listaAlunos.add(linhaAlunos); // Adicionar dados do Aluno à lista de Alunos
        ordenarLista(); // Ordenar por numero a lista Alunos

        System.out.println("\n> Aluno adicionado com sucesso à lista.");
    }

    private static void ordenarLista() {
        List<String> listaSemCabecalho = listaAlunos.subList(2, listaAlunos.size());

        List<String> listaOrdenadaInversamente = listaSemCabecalho.stream()
                .sorted(Comparator.comparing(Programa::extrairNumeroAluno).reversed())
                .toList();

        // Meter novamente as linhas de cima (descrução e exemplo)
        List<String> listaFinal = new java.util.ArrayList<>(listaAlunos.size());
        listaFinal.add(listaAlunos.get(0));
        listaFinal.add(listaAlunos.get(1));
        listaFinal.addAll(listaOrdenadaInversamente);

        listaAlunos = listaFinal;
    }

    private static String extrairNumeroAluno(String aluno) {
        return aluno.split(";")[1].trim();
    }


    private static boolean AlunoExiste(String numeroAluno, String turma) {
        for (String linha : listaAlunos) {
            String[] partes = linha.split(";");
            if (partes[1].trim().equals(numeroAluno.trim()) && partes[0].trim().equals(turma.trim())) {
                System.out.println("> Numero " + numeroAluno + " já atribuído na turma " + turma + " a: " + partes[2]);
                return true;
            }
        }
        return false;
    }

    private static void adicionarNota() {
        String numeroAluno;
        String turma;
        String disciplina;
        double nota;
        String nome = "";

        // Pedir turma de aluno
        while (true) {
            try {
                System.out.print("(0 para cancelar) Turma de aluno: ");
                turma = scan.nextLine().trim();
                turma = turma.toUpperCase();
                if (turma.equals("0")) {
                    return;
                } else if (turma.matches("^\\d[A-Z]$") || turma.matches("^\\d{2}[A-Z]$") ) {
                    break;
                } else {
                    System.out.println("> Insira uma turma válida. Formato de turma válida: 0N (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("> Turma inválida");
            }
        }

        // Pedir número de aluno
        while (true) {
            try {
                System.out.print("(0 para cancelar) Número do aluno: ");
                numeroAluno = scan.nextLine().trim();
                if (numeroAluno.equals("0")) {
                    return;
                } else if (!numeroAluno.isEmpty() && numeroAluno.matches("\\d+")) {
                    break;
                } else {
                    System.out.println("> Insira um número válido. (0 para cancelar)");
                }
            } catch (Exception e) {
                System.out.println("> Número inválido");
            }
        }

        // Verificar se o aluno existe e obter o nome
        boolean alunoEncontrado = false;
        for (String linha : listaAlunos) {
            String[] partes = linha.split(";");
            if (partes[1].trim().equals(turma) && partes[2].trim().equals(numeroAluno)) {
                nome = partes[3];
                alunoEncontrado = true;
                break;
            }
        }

        if (!alunoEncontrado) {
            System.out.println("> Aluno não encontrado com a turma e número fornecidos.");
            return;
        }

        // Pedir Disciplina
        while (true) {
            try {
                System.out.print("Disciplina: ");
                disciplina = scan.nextLine().trim();
                if (!disciplina.isEmpty() && disciplina.matches("^[A-Za-záéíóúÁÉÍÓÚâêîôûÂÊÎÔÛãõÃÕàèìòùÀÈÌÒÙçÇ ]+$")) {
                    break;
                } else {
                    System.out.println("> Insira uma disciplina válida.");
                }
            } catch (Exception e) {
                System.out.println("> Disciplina inválida");
            }
        }

        // Pedir Nota
        while (true) {
            try {
                System.out.print("Nota: ");
                nota = Double.parseDouble(scan.nextLine());
                if (nota >= 0 && nota <= 20) {
                    break;
                } else {
                    System.out.println("> Insira uma nota válida entre 0 e 20.");
                }
            } catch (Exception e) {
                System.out.println("> Nota inválida");
            }
        }

        Nota notas = new Nota(nome, numeroAluno, turma, disciplina, nota);
        String linhaNotas = notas.getTurma() + ";" + notas.getNumeroAluno() + ";" + notas.getNome() + ";" + notas.getDisciplina() + ";" + notas.getNota();
        listaNotas.add(linhaNotas); // Adicionar dados da Nota à lista de Notas

        System.out.println("\n> Nota adicionada com sucesso.");
    }

    private static void verAlunos(){
        System.out.println();
        if (listaAlunos.size() > 1) {
            for (int i=0; i<listaAlunos.size();){
                for (String listaAluno : listaAlunos) {
                    String[] a = listaAluno.split(";");
                    if (i != 1){
                        System.out.println(Arrays.toString(a));
                    }
                    i++;
                }
            }

        } else {
            System.out.println("> Não existem notas registadas.");
        }
    }

    private static void verNotas() {
        System.out.println();
        if (listaNotas.size() > 1) {
            for (int i=0; i<listaNotas.size();){
                for (String listaNota : listaNotas) {
                String[] a = listaNota.split(";");
                    if (i != 1){
                        System.out.println(Arrays.toString(a));
                    }
                    i++;
                }
            }

        } else {
            System.out.println("> Não existem notas registadas.");
        }
    }

    private static void pesquisarNotas() {
        System.out.println();
        int opcaoPesquisa;
        String termoPesquisa;

        // Pedir por que pesquisar
        while (true) {
            try {
                System.out.println("Escolha como pesquisar notas:");
                System.out.println("[1] Por Turma");
                System.out.println("[2] Por Nome");
                System.out.println("[3] Por Disciplina");
                System.out.print("Escolha uma opção: ");
                opcaoPesquisa = scan.nextInt();

                if (opcaoPesquisa == 1 || opcaoPesquisa == 2 || opcaoPesquisa == 3) {
                    scan.nextLine();
                    break;
                } else {
                    System.out.println("> Opção inválida. Escolha 1, 2 ou 3.");
                }
            } catch (Exception e) {
                System.out.println("> Opção inválida");
            }
        }

        switch (opcaoPesquisa) {
            case 1:
                while (true) {
                    try {
                        System.out.print("Pesquisar notas por Turma: ");
                        termoPesquisa = scan.nextLine().trim().toUpperCase();
                        if (!termoPesquisa.isEmpty() && termoPesquisa.matches("^\\d[A-Z]$") || termoPesquisa.matches("^\\d{2}[A-Z]$")) {
                            break;
                        } else {
                            System.out.println("> Insira uma turma válida. Formato de turma válida: 0N (0 para cancelar)");
                        }
                    } catch (Exception e) {
                        System.out.println("> Turma inválida");
                    }
                }
                break;

            case 2:
                while (true) {
                    try {
                        System.out.print("Pesquisar notas por nome: ");
                        termoPesquisa = scan.nextLine().trim();
                        if (!termoPesquisa.isEmpty() && !termoPesquisa.matches(".*\\d.*")) {
                            break;
                        } else {
                            System.out.println("> Insira um nome válido.");
                        }
                    } catch (Exception e) {
                        System.out.println("> Nome inválido");
                    }
                }
                break;

            case 3:
                while (true) {
                    try {
                        System.out.print("Pesquisar notas por disciplina: ");
                        termoPesquisa = scan.nextLine().trim();
                        if (!termoPesquisa.isEmpty() && termoPesquisa.matches("^[A-Za-záéíóúÁÉÍÓÚâêîôûÂÊÎÔÛãõÃÕàèìòùÀÈÌÒÙçÇ ]+$")) {
                            break;
                        } else {
                            System.out.println("> Insira uma disciplina válida.");
                        }
                    } catch (Exception e) {
                        System.out.println("> Disciplina inválida");
                    }
                }
                break;

            default:
                System.out.println("> Opção inválida.");
                return;
        }

        // Fazer a pesquisa
        List<String> result;
        switch (opcaoPesquisa) {
            case 1:
                String pesquisaTurma = termoPesquisa;
                result = listaNotas.stream()
                        .filter(line -> {
                            String[] partes = line.split(";");
                            return partes[1].equals(pesquisaTurma);
                        })
                        .toList();
                break;

            case 2:
                String pesquisaNome = termoPesquisa;
                result = listaNotas.stream()
                        .filter(line -> {
                            String[] partes = line.split(";");
                            return partes[3].equalsIgnoreCase(pesquisaNome);
                        })
                        .toList();
                break;

            case 3:
                String pesquisaDisciplina = termoPesquisa;
                result = listaNotas.stream()
                        .filter(line -> {
                            String[] partes = line.split(";");
                            return partes[4].equalsIgnoreCase(pesquisaDisciplina);
                        })
                        .toList();
                break;

            default:
                System.out.println("> Opção inválida.");
                return;
        }

        // Exibir o resultado da pesquisa
        if (result.isEmpty()) {
            System.out.println("> Não foram encontradas notas para o critério de pesquisa: " + termoPesquisa);
        } else {
            System.out.println("\nResultados da pesquisa:");
            for (String linha : result) {
                System.out.println(linha);
            }
        }
    }

//    private static void filtrarNotas() {
//        System.out.println();
//        String disciplina;
//        double notaMinima;
//        double notaMaxima;
//
//        // Pedir Disciplina
//        while (true) {
//            try {
//                System.out.print("Filtrar notas por disciplina: ");
//                disciplina = scan.nextLine().trim();
//                if (!disciplina.isEmpty() && disciplina.matches("^[A-Za-záéíóúÁÉÍÓÚâêîôûÂÊÎÔÛãõÃÕàèìòùÀÈÌÒÙçÇ ]+$")) {
//                    break;
//                } else {
//                    System.out.println("> Insira uma disciplina válida.");
//                }
//            } catch (Exception e) {
//                System.out.println("> Disciplina inválida");
//            }
//        }
//
//        // Pedir Nota Mínima
//        while (true) {
//            try {
//                System.out.print("Nota mínima: ");
//                notaMinima = Double.parseDouble(scan.nextLine());
//                if (notaMinima >= 0 && notaMinima <= 20) {
//                    break;
//                } else {
//                    System.out.println("> Insira uma nota válida entre 0 e 20.");
//                }
//            } catch (Exception e) {
//                System.out.println("> Nota mínima inválida");
//            }
//        }
//
//        // Pedir Nota Máxima
//        while (true) {
//            try {
//                System.out.print("Nota máxima: ");
//                notaMaxima = Double.parseDouble(scan.nextLine());
//                if (notaMaxima >= 0 && notaMaxima <= 20 && notaMaxima>=notaMinima) {
//                    break;
//                } else {
//                    System.out.println("> Insira uma nota válida entre 0 e 20.");
//                    scan.reset();
//                }
//            } catch (Exception e) {
//                System.out.println("> Nota máxima inválida");
//                scan.reset();
//            }
//        }
//
//        double notaMinimaStream = notaMinima;
//        double notaMaximaStream = notaMaxima;
//        String disciplinaStream = disciplina;
//
//        List<String> result = listaNotas.stream()
//                .filter(line -> {
//                    String[] parts = line.split(";");
//                    String disc = parts[3];
//                    double nota = Double.parseDouble(parts[4]);
//                    return disc.equalsIgnoreCase(disciplinaStream) && nota >= notaMinimaStream && nota <= notaMaximaStream;
//                })
//                .toList();
//
//        if (result.isEmpty()) {
//            System.out.println("> Não existem notas registadas para a disciplina: " + disciplina + " entre " + notaMinima + " e " + notaMaxima);
//        } else {
//            System.out.println("\nNotas para " + disciplina + " entre " + notaMinima + " e " + notaMaxima + ":");
//            for (String linha : result) {
//                System.out.println(linha);
//            }
//        }
//    }
}
