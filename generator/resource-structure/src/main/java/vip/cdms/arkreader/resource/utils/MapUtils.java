package vip.cdms.arkreader.resource.utils;

import java.util.Map;

public class MapUtils {
    public interface EntryConsumer<K, V> {
        void accept(K key, V value);
    }

    public static <K, V> void forEach(Map<K, V> map, EntryConsumer<K, V> consumer) {
        for (Map.Entry<K, V> entry : map.entrySet())
            consumer.accept(entry.getKey(), entry.getValue());
    }

    @SuppressWarnings("Java8MapApi")
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }
}
