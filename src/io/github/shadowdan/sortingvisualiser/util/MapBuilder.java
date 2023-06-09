package io.github.shadowdan.sortingvisualiser.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

    private final Map<K, V> map = new HashMap<>();

    public static <K, V> MapBuilder<K, V> builder(K index, V color) {
        return new MapBuilder<K, V>().add(index, color);
    }

    private MapBuilder() { }

    public MapBuilder<K, V> add(K index, V color) {
        map.put(index, color);
        return this;
    }

    public Map<K, V> build() {
        return map;
    }
}
