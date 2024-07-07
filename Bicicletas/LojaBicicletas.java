package Bicicletas;

import java.util.*;

public class LojaBicicletas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bicicleta> bicicletas = new ArrayList<>();

        boolean continuar = true;

        int beID = 1; // 'Contador' de bicicletas elétricas
        int bID = 1; // 'Contador' de bicicletas não elétricas
        String tipo; // Variavel para dizer se é tipo Elétrica ou não elétrica

        while (continuar) {

            String beID_ID = "E"+beID; // E = Elétrica
            String bID_ID = "NE"+bID; // NE = Não Elétrica

            System.out.println("Nome da bicicleta:");
            String nome = scanner.nextLine();

            System.out.println("Número de mudanças (ex: 6, 10):");
            String mudancas = scanner.nextLine();

            System.out.println("Tipo de roda (ex: todo-o-terreno, estrada, comum):");
            String roda = scanner.nextLine();

            System.out.println("A bicicleta é elétrica? (sim/não)");
            String resposta = scanner.nextLine();

            // Verificar se a bicicleta é elétrica ou não
            if (resposta.equalsIgnoreCase("sim")) {
                tipo = "Elétrica";

                BicicletaEletrica bicicleta = new BicicletaEletrica();
                bicicleta.setTipo(tipo);
                bicicleta.setNome(nome);
                bicicleta.setMudancas(mudancas);
                bicicleta.setRoda(roda);
                bicicleta.setMotor(true);
                bicicleta.setId(beID_ID);
                bicicletas.add(bicicleta);

                beID++;
            } else {
                tipo = "Não Elétrica";

                Bicicleta bicicleta = new Bicicleta();
                bicicleta.setTipo(tipo);
                bicicleta.setNome(nome);
                bicicleta.setMudancas(mudancas);
                bicicleta.setRoda(roda);
                bicicleta.setId(bID_ID);
                bicicletas.add(bicicleta);

                bID++;
            }

            System.out.println("Deseja adicionar outra bicicleta? (sim/não)");
            String continuarResposta = scanner.nextLine();
            if (continuarResposta.equalsIgnoreCase("não") || continuarResposta.equalsIgnoreCase("nao")) {
                continuar = false;
            }
        }

        // Pedir ao usuário como ele gostaria de ordenar a lista
        System.out.println("\nComo gostaria de ordenar a lista? (nome/tipo/mudancas)");
        String comoOrdenar = scanner.nextLine();

        // Ordenar a lista de acordo com o critério escolhido
        if (comoOrdenar.equals("nome")) {
            ordenarPorNome(bicicletas);
        } else if (comoOrdenar.equals("mudancas")||comoOrdenar.equals("mudanças")) {
            ordenarPorMudancas(bicicletas);
        } else if (comoOrdenar.equals("tipo")) {
            ordenarPorTipo(bicicletas);
        } else {
            System.out.println("\nImpossivel ordenar dessa maneira. Lista não ordenada a ser exibida...\n");
        }

        // Fazer display das informações das bicicletas
        System.out.println("\nBicicletas:");
        for (Bicicleta b : bicicletas) {
            if (b instanceof BicicletaEletrica) { // Caso seja elétrica
                System.out.println("\nID: " + b.getId());
                System.out.println("• Nome: " + b.getNome());
                System.out.println("• Mudanças: " + b.getMudancas());
                System.out.println("• Tipo de roda: " + b.getRoda());
                System.out.println("• Tipo: " + b.getTipo());
                System.out.println("• Possui motor"); // Diferença
            } else { // Caso não seja elétrica
                System.out.println("\nID: " + b.getId());
                System.out.println("• Nome: " + b.getNome());
                System.out.println("• Mudanças: " + b.getMudancas());
                System.out.println("• Tipo de roda: " + b.getRoda());
                System.out.println("• Tipo: " + b.getTipo());
                System.out.println("• Não Possui motor"); // Diferença
            }
        }

    }

    public static void ordenarPorNome(List<Bicicleta> bicicletas) {
        Collections.sort(bicicletas, Comparator.comparing(Bicicleta::getNome));
    }

    public static void ordenarPorMudancas(List<Bicicleta> bicicletas) {
        Collections.sort(bicicletas, Comparator.comparing(Bicicleta::getMudancas));
    }

    public static void ordenarPorTipo(List<Bicicleta> bicicletas) {
        Collections.sort(bicicletas, Comparator.comparing(Bicicleta::getTipo));
    }
}
