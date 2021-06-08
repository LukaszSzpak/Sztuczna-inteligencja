import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Process {

    static Map<String, Integer> transformStringToWordMap(String subj) {
        String delimiters = "\\s+|,\\s*|\\.\\s*";
        String[] wordArray = subj.split(delimiters);
        Map<String, Integer> wordMap = new HashMap<>();

        for (String word : wordArray) {
            if (word.length() > 1)
                if (wordMap.containsKey(word))
                    wordMap.put(word, wordMap.get(word) + 1);
                else
                    wordMap.put(word, 1);
        }

        return wordMap;
    }

    static Map<Integer, Integer> getLabel3Count(List<Review> reviewList) {
        Map<Integer, Integer> classesMap = new HashMap<>();
        for (Review review : reviewList) {
            if (classesMap.containsKey(review.getLabel3()))
                classesMap.put(review.getLabel3(), classesMap.get(review.getLabel3()) + 1);
            else
                classesMap.put(review.getLabel3(), 1);
        }

        return classesMap;
    }

    static Map<String, Integer> getWordCountMap(List<Review> reviewList) {
        Map<String, Integer> sumMap = new HashMap<>();
        for (Review review : reviewList) {
            Map<String, Integer> tempMap = Process.transformStringToWordMap(review.getSubj());
            Map<String, Integer> finalSumMap = sumMap;
            tempMap.forEach((k, v) -> finalSumMap.merge(k, v, Integer::sum));
        }

        sumMap = sumMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)
                );

        return sumMap;
    }

    static void saveToArffFile(List<Review> reviews, List<String> words) throws FileNotFoundException {
        PrintWriter outFile = new PrintWriter("/Users/lukasz/Desktop/studia/Sztuczna_intel/Cwiczenie_4/data.arff");

        outFile.println("@relation review\n");
        outFile.println("@attribute word numeric");
        outFile.println("@attribute label3 numeric");
        outFile.println("@attribute rating numeric\n");
        outFile.println("@data");
        for (Review review : reviews)
            outFile.print(review.myToString(words));
    }

}
