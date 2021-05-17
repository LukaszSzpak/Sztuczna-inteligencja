import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataOperations {
    private String[] names;
    private List<Pair<String, List<Review>>> reviewList;
    private final String path = "/Users/lukasz/Desktop/studia/Sztuczna_intel/Cwiczenie_4/scale_data/scaledata/";
    private final String arffFilePath = "/Users/lukasz/Desktop/studia/Sztuczna_intel/Cwiczenie_4/data.arff";
    private final String[] fileNames = new String[]{"id", "label.3class", "label.4class", "rating", "subj"};

    public DataOperations(String[] names) {
        this.names = names;
    }

    public void readData() throws FileNotFoundException {
        this.reviewList = new LinkedList<>();

        for(String name : this.names) {
            List<Review> actualNameReviews = new LinkedList<>();
            Scanner scanner = new Scanner(new FileReader(new File(path + name + "/" + fileNames[0] + "." + name)));
            Scanner scanner1 = new Scanner(new FileReader(new File(path + name + "/" + fileNames[1] + "." + name)));
            Scanner scanner2 = new Scanner(new FileReader(new File(path + name + "/" + fileNames[2] + "." + name)));
            Scanner scanner3 = new Scanner(new FileReader(new File(path + name + "/" + fileNames[3] + "." + name)));
            Scanner scanner4 = new Scanner(new FileReader(new File(path + name + "/" + fileNames[4] + "." + name)));

                while (scanner.hasNext() &&
                        scanner1.hasNext() &&
                        scanner2.hasNext() &&
                        scanner3.hasNext() &&
                        scanner4.hasNext()) {
                    actualNameReviews.add(new Review(
                            scanner.nextInt(),
                            scanner1.nextInt(),
                            scanner2.nextInt(),
                            Float.parseFloat(scanner3.nextLine()),
                            scanner4.nextLine()
                    ));
                }
            this.reviewList.add(new Pair<>(name, actualNameReviews));
        }
    }

}
