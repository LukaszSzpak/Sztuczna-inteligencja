import java.util.Scanner;

public class Human extends Player{

    public Human(String name, Analysis analyzer) {
        super(name, analyzer);
    }

    public Human(Human human) {
        super(human);
    }

    @Override
    public int move(Mancala mancala) {
        this.analyzer.addMove();
        System.out.println("Turn: " + this.name);
        System.out.println("Enter next field number (0-5)");
        return new Scanner(System.in).nextInt() % 6;
    }

}
