public class MapEntry<K, V> {
    private K key;
    private V value;

    /***
     * constructor
     * 
     * @param k
     * @param v
     */
    public MapEntry(K k, V v) {
        key = k;
        value = v;
    }

    /***
     * getter method for key
     * 
     * @return
     */
    public K getKey() {
        return key;
    }

    /***
     * getter method for value
     * 
     * @return
     */
    public V getValue() {
        return value;
    }

    /***
     * setter method for key
     * 
     * @param k
     */
    public void setKey(K k) {
        key = k;
    }

    /***
     * setter method for value
     * 
     * @param v
     */
    public void setValue(V v) {
        value = v;
    }

    /***
     * formats output
     * 
     * @return
     */
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
