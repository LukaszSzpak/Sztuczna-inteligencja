import java.util.Scanner;

public class Human extends Player{

    public Human() {
        super();
    }

    @Override
    public int move() {
        System.out.print("Enter next field number (0-5)");
        return new Scanner(System.in).nextInt();
    }

}
