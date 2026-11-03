import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter an integer: ");
        int num = sc.nextInt();

        if (num > 0) {
            System.out.println("Positive Number");
        }
        else if (num < 0) {
            System.out.println("Negative Number");
        }
        else {
            System.out.println("Zero");
        }

        sc.close();
    }
}