import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first number: ");
        int a = scanner.nextInt();

        System.out.print("Enter the second number: ");
        int b = scanner.nextInt();

        int sum = add(a, b);

        System.out.println("The sum of " + a + " and " + b + " is: " + sum);
    }

    private static int add(int a, int b) {
        return a + b;
    }
}