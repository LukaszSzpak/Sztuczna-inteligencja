import java.util.Optional;

public class StartApp {
    public static void main(String[] args) throws NoSuchMethodException {
        Mancala mancala = new Mancala(new Human("HumanA"), new AI("AI1", "min-max"));
        mancala.printGame();

        while (!mancala.theEndOfGame()) {
            mancala.nextMove(Optional.empty());
            mancala.printGame();
        }

    }
}
