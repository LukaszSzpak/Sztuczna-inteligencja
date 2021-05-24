import java.util.HashMap;
import java.util.Map;

public class Process {

    static Map<String, Integer> transformStringToWordMap(String subj) {
        String delimiters = "\\s+|,\\s*|\\.\\s*";
        String[] wordArray = subj.split(delimiters);
        Map<String, Integer> wordMap = new HashMap<>();

        for (String word : wordArray) {
            if (wordMap.containsKey(word))
                wordMap.put(word, wordMap.get(word) + 1);
            else
                wordMap.put(word, 1);
        }

        return wordMap;
    }
}
