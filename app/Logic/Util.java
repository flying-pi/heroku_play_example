package Logic;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yurabraiko on 11.05.17.
 */
public class Util {

    public static String clearWord(String str) {
        return str
                .replaceAll("[^A-Za-z]", " ")
//                .replaceAll("[^А-Яа-я]", " ")
                .replaceAll("  ", " ")
                .replaceAll("\\b\\w{1,2}\\b\\s?", "")
                ;
    }

    public static <K, V > Map<K, V> sortByValue(Map<K, V> map, Comparator<? super V> comparator) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

}
