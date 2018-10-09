import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> rs = new LinkedHashMap<>();
        ArrayList<Map.Entry<K,V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        for (Map.Entry<K, V> pair : list)
            rs.put(pair.getKey(), pair.getValue());
        return rs;
    }


    public static void main(String[] args) {

    }

}
