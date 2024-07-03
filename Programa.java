import java.util.Scanner;

public class Programa {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Criar objeto scanner

        // Pedir um valor String
        System.out.println("String:");
        String valorString = scanner.nextLine();

        // Pedir um valor int
        System.out.println("Int:");
        int valorInt = scanner.nextInt();

        // Pedir um valor char
        System.out.println("Char:");
        char valorChar = scanner.next().charAt(0);

        // Pedir um valor double
        System.out.println("Double:");
        double valorDouble = scanner.nextDouble();

        limparBufferInput(scanner); // Utilizar a função limparBufferInput para limpar o buffer

        // Pedir novamente o valor String
        System.out.println("String novamente:");
        valorString = scanner.nextLine(); // 'valorString' em vez de 'String valorString' porque estamos a reutilizar a variável

        // Fazer display dos valores todos numa única linha
        System.out.print("Valor String: " + valorString + ", Valor int: " + valorInt + ", Valor char: " + valorChar + ", Valor double: " + valorDouble);
    }

    private static void limparBufferInput(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

}
