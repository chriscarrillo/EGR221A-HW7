package datastructures.dictionaries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import egr221a.exceptions.NotYetImplementedException;
import egr221a.interfaces.trie.BString;
import egr221a.interfaces.trie.TrieMap;

/**
 * See egr221a/interfaces/trie/TrieMap.java
 * and egr221a/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        clear();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode current = (HashTrieNode) this.root;
        for (A characters : key) {
            if (current.pointers.get(characters) == null) {
                current.pointers.put(characters, new HashTrieNode());
            }
            current = current.pointers.get(characters);
        }

        V previousValue = current.value;
        current.value = value;
        if (previousValue == null) {
            this.size++;
        }
        return previousValue;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode current = (HashTrieNode) this.root;
        for (A letter : key) {
            HashTrieNode next = current.pointers.get(letter);
            if (next == null) {
                return null;
            }
            current = next;
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode current = (HashTrieNode) this.root;
        for (A letter : key) {
            HashTrieNode next = current.pointers.get(letter);
            if (next == null) {
                return false;
            }
            current = next;
        }
        return true;
    }

    @Override
    public void delete(K key) {
        boolean validKey = true;
        if (key == null){
            throw new IllegalArgumentException("No key was given");
        }

        HashTrieNode current = (HashTrieNode) this.root;
        if (key.isEmpty()) {
            if (current.value != null) {
                current.value = null;
                size--;
            }
        } else {
            HashTrieNode previous = (HashTrieNode) this.root;
            Iterator<A> iterator = key.iterator();

            A cutBranch = (A) iterator.next();
            for (A character : key){
                if (current.pointers.get(character) != null) {
                    if (current.pointers.size() >= 2 || current.value != null) {
                        previous = current;
                        cutBranch = character;
                    }
                    current = current.pointers.get(character);
                    } else {
                        validKey = false;
                        break;
                }
                if (validKey) {
                    if (current.pointers.isEmpty()) {
                        previous.pointers.get(cutBranch);
                        size--;
                    } else {
                        if (current.value != null) {
                            current.value = null;
                            size--;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
        this.size = 0;
    }
}
