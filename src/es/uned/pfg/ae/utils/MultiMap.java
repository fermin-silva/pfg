package es.uned.pfg.ae.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Fermin Silva < fermins@olx.com >
 */
public class MultiMap implements Map<String, String[]> {

    private final Map<String, String[]> mapa;


    public MultiMap(Map<String, String[]> mapa) {
        this.mapa = mapa;
    }

    public String getPrimero(String key, String defecto) {
        String primero = getPrimero(key);

        return primero != null? primero : defecto;
    }

    public String getPrimero(String key) {
        String[] valores = this.mapa.get(key);

        if (valores != null && valores.length > 0) {
            return valores[0];
        }
        else {
            return null;
        }
    }

    @Override
    public int size() {
        return mapa.size();
    }

    @Override
    public boolean isEmpty() {
        return mapa.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mapa.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mapa.containsValue(value);
    }

    public String[] get(Object key, String defecto) {
        String[] valores = mapa.get(key);

        return valores != null && valores.length > 0 ? valores :
                                                       new String[] { defecto };
    }

    @Override
    public String[] get(Object key) {
        return mapa.get(key);
    }

    @Override
    public String[] put(String key, String[] value) {
        return mapa.put(key, value);
    }

    @Override
    public String[] remove(Object key) {
        return mapa.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String[]> m) {
        mapa.putAll(m);
    }

    @Override
    public void clear() {
        mapa.clear();
    }

    @Override
    public Set<String> keySet() {
        return mapa.keySet();
    }

    @Override
    public Collection<String[]> values() {
        return mapa.values();
    }

    @Override
    public Set<Entry<String, String[]>> entrySet() {
        return mapa.entrySet();
    }
}
