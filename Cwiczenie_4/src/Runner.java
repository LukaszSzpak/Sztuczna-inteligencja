import java.io.FileNotFoundException;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException {
        String[] names = new String[] {
                "Dennis+Schwartz",
                "James+Berardinelli",
                "Scott+Renshaw",
                "Steve+Rhodes"
        };

        DataOperations dataOperations = new DataOperations(names);
        dataOperations.readData();
    }
}
