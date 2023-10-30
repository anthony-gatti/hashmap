import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class HashMap an implementation of the hash table
 * data structure using quadratic probing
 */
public class HashMapQP<K, V> {
    private int size;
    private double loadFactor;
    private MapEntry<K, V>[] hashTable;
    static int iterations = 0;
    static int collisions = 0;

    /**
     * Default constructor
     * Creates an empty hash table with capacity 100
     * and default load factor of 0.5
     */
    public HashMapQP() {
        this(100, 0.5);
    }

    /**
     * Constructor with one parameters
     * Creates an empty hash table with capacity c
     * and default load factor of 0.5
     */
    public HashMapQP(int c) {
        this(c, 0.5);
    }

    /**
     * Constructor with two parameters
     * Creates an empty hash table with capacity c
     * and load factor lf
     */
    public HashMapQP(int c, double lf) {
        hashTable = new MapEntry[trimToPrime(c)];
        loadFactor = lf;
        size = 0;
        collisions = 0;
    }

    /**
     * Private method to find the closest power of 2
     * to the capacity of the hash table
     * 
     * @param c desired capacity for the hash table
     * @return closest power of 2 to c
     */
    private int trimToPrime(int c) {
        if (c <= 1) {
            return trimToPrime(++c);
        }
        for (int i = 2; i < c; i++) {
            if (c % i == 0) {
                return trimToPrime(++c);
            }
        }
        return c;
    }

    /**
     * hash method
     * 
     * @param hashCode
     * @return valid index in the hash table
     */
    private int hash(int hashCode) {
        return hashCode & (hashTable.length - 1);
    }

    /**
     * Rehash method called when the size of the hashtable
     * reached lf * capacity
     */
    private void rehash() {
        MapEntry<K, V>[] list = toList();
        hashTable = new MapEntry[hashTable.length << 1];
        size = 0;
        for (int i = 0; i < list.length; i++) {
            put(list[i].getKey(), list[i].getValue());
        }

    }

    /**
     * Method size
     * 
     * @return the number of elements added to the hash table
     */
    public int size() {
        return size;
    }

    /**
     * Method clear
     * resets all the hash table elements to null
     * and clears all the linked lists attached to the hash table
     */
    public void clear() {
        size = 0;
        for (int i = 0; i < hashTable.length; i++)
            if (hashTable[i] != null)
                hashTable[i] = null;
    }

    /**
     * Method isEmpty
     * 
     * @return true if there are no valid data in the hash table
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Search method
     * 
     * @param key being searched for
     * @return true if key is found, false otherwise
     */
    public boolean containsKey(K key) {
        if (get(key) != null)
            return true;
        return false;
    }

    /**
     * Get method
     * 
     * @param key being searched for
     * @return the value of the hash table entry if the key is found,
     *         null if the key is not found
     */
    public V get(K key) {
        iterations = 0;
        int HTIndex = hash(key.hashCode());
        int initialIndex = HTIndex;
        int i = 1;
        while (hashTable[HTIndex] != null) {
            iterations++;
            if (hashTable[HTIndex].getKey().equals(key)) {
                return hashTable[HTIndex].getValue();
            }
            HTIndex = (initialIndex + i * i) % hashTable.length;
            i++;
        }
        iterations++;
        return null;
    }

    /**
     * Add a new entry to the hash table
     * 
     * @param key   key of the entry to be added
     * @param value value of the entry to be added
     * @return the old value of the entry if an entry with the same key is found
     *         the parameter value is returned if a new entry has been added
     */

    public V put(K key, V value) {
        if (get(key) != null) { // The key is in the hash map
            int HTIndex = hash(key.hashCode());
            int initialIndex = HTIndex;
            int i = 1;
            while (hashTable[HTIndex] != null) {
                MapEntry<K, V> ll = hashTable[HTIndex];
                if (ll.getKey().equals(key)) {
                    V old = ll.getValue();
                    ll.setValue(value);
                    hashTable[HTIndex] = ll;
                    return old;
                }
                HTIndex = (initialIndex + i * i) % hashTable.length;
                ll = hashTable[HTIndex];
                i++;
            }

        }

        if (size >= hashTable.length * loadFactor) {
            rehash();
        }
        int HTIndex = hash(key.hashCode());

        if (hashTable[HTIndex] == null) {
            hashTable[HTIndex] = new MapEntry<K, V>(key, value);
            size++;
            return value;
        }
        if (hashTable[HTIndex] != null) {
            int initialIndex = HTIndex;
            for (int i = 1; hashTable[HTIndex] != null; i++) {
                HTIndex = (initialIndex + i * i) % hashTable.length;
            }
        }
        collisions++;
        hashTable[HTIndex] = new MapEntry<K, V>(key, value);
        size++;
        return value;
    }

    /**
     * Method toList used by rehash
     * 
     * @return all the entries in the hash table in an array list
     */
    public MapEntry<K, V>[] toList() {
        MapEntry<K, V>[] list = new MapEntry[size];
        int j = 0;
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                list[j] = hashTable[i];
                j++;
            }
        }
        return list;
    }

    /**
     * toString method
     * 
     * @return the hashtable entries formatted as a string
     */
    public String toString() {
        String out = "[";
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                out += hashTable[i].toString();
                out += "\n";
            }
        }
        out += "]";
        return out;
    }

    public int getIterations() {
        return iterations;
    }

    public int getCollisions() {
        return collisions;
    }

}