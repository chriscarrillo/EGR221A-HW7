package datastructures.extracredit;

import datastructures.dictionaries.HashTrieMap;
import datastructures.worklists.CircularArrayFIFOQueue;
import datastructures.worklists.ListFIFOQueue;
import egr221a.interfaces.worklists.FIFOWorkList;
import egr221a.interfaces.worklists.FixedSizeFIFOWorkList;
import egr221a.types.ByteString;

public class SuffixTrie extends HashTrieMap<Byte, ByteString, Boolean> {
    protected static final Byte TERMINATOR = null;
    
    /* Here are some fields you will need to start you off. You might need others... */
    private FixedSizeFIFOWorkList<Byte> currentMatch;
    private FIFOWorkList<HashTrieNode> leaves;
    private HashTrieNode lastMatch;
    private FixedSizeFIFOWorkList<Byte> contents;
    private int size;
    private int maxMatchLength;

    /**
     * A new SuffixTrie is constructed with an internal buffer of size size and a
     * maximum allowed match length of maxMatchLength.
     *
     * @throws IllegalArgumentException if maxMatchLength < 0
     * @param size the size of the internal contents of the trie
     * @param maxMatchLength the maximum allowed match length
     */
    public SuffixTrie(int size, int maxMatchLength) {
        super(ByteString.class);

        if(maxMatchLength < 0) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        this.maxMatchLength = maxMatchLength;
        clear();
    }

    /**
     * Finds the longest matching suffix in the trie for a prefix of buffer.
     * To do this, we gradually shift elements from buffer to match as we 
     * determine that they are actually a match to a suffix in the trie.
     * 
     * @postcondition currentMatch == suffix for the longest possible _partial_
     *                suffix in the trie
     * @postcondition the node representing the last matched character in the trie
     *                is stored in this.lastMatch (we might need this later)
     *
     * Note that this method is not guaranteed to find a complete match -- it may,
     * in some cases, make a partial match.
     *
     * We will find a COMPLETE match when we use the buffer to traverse the tree
     * from the root to any leaf. It indicates that the next segment of the buffer
     * is exactly one of the suffixes in the trie.
     *
     * We will find a PARTIAL match when we use the buffer to traverse the tree from
     * the root, but do NOT reach a leaf.  For example, if...
     *     buffer = ['a', 'b', 'c']
     *     trie = {"abcde", "bcde", "cde", "de", "e"}
     * Then, the longest match is "abc", but this isn't a complete word in the trie.
     * There is definitely a match; it's just a partial one.
     *
     * If you find a COMPLETE match, you should return the total number of bytes you've
     * matched against the buffer. Otherwise, you should return zero.
     *
     * When implementing this method, you should start by resetting this.lastMatch,
     * then start traversing from the root of the trie to try finding the new match. You
     * should not traverse starting from the old value of this.lastMatch.  Make sure
     * to update this.lastMatch after finishing traversing.
     * 
     * @param buffer the buffer to search with 
     * @return the total number of bytes matched
     */
    public int startNewMatch(FIFOWorkList<Byte> buffer) {
        HashTrieNode current = (HashTrieNode)this.root;
        currentMatch.clear();
        lastMatch = current;
        while(buffer.hasWork()) {
            Byte b = buffer.peek();
            if(current.pointers.containsKey(b)) {
                current = current.pointers.get(b);
                currentMatch.add(b);
                buffer.next();
            } else {
                lastMatch = current;
                if(current.pointers.containsKey(TERMINATOR)) {
                    return currentMatch.size();
                } else {
                    return 0;
                }
            }
        }
        if(current.pointers.containsKey(TERMINATOR)) {
            return currentMatch.size();
        } else {
            return 0;
        }
    }

    /**
     * Extends this.currentMatch to handle duplicates.
     *
     * Consider the case where the buffer is:
     *     abcabcabcd
     * A good decomposition of this buffer would be:
     *     abc abc abc d
     * LZ77 can capture this idea by using *the match itself* as part of the match.
     * On the first match, we will get just 'a'. Then, just 'b'.  Then, just 'c'.
     * Now, our suffix trie is populated with "abc", "bc", and "c".
     * When we next try to match, we clearly can find "abc":
     *     abc|abcabcd
     *         ***
     *         ^--^
     *
     * The interesting idea is that the match can *continue*.  Because the next
     * character in the buffer ('a') matches the next character in the already
     * consumed window ('a'), we can continue the match.  In fact, this can
     * continue indefinitely.
     *     abc|abcabcd
     *         ***
     *         ^--^
     *          ^--^
     *           ^--^
     *     abc|abcabcd
     *            ^--x
     *
     * Eventually, it will stop matching (see above at the 'd').  Then, we output the
     * entire match.
     *
     * This special case of the LZ77 algorithm interestingly is where much of the
     * compression comes from.
     *
     * @param buffer the buffer to search against
     * @return the total number of bytes matched
     */
    public int extendMatch(FIFOWorkList<Byte> buffer) {
        // Note: this method has been provided for you. You should not make any
        // changes to this method.
        int numMatches = 0;
        while (buffer.hasWork() && 
               this.currentMatch.size() < this.currentMatch.capacity() - 1 && 
               this.currentMatch.peek(numMatches) == buffer.peek()) {
            this.currentMatch.add(buffer.next());
            numMatches += 1;
        }
        return numMatches;
    }

    /**
     * Adds the given byte to this.currentMatch. This method should
     * NOT change this.lastMatch.
     *
     * If the client tries adding a byte after this.currentMatch is full,
     * you should do nothing.
     *
     * @param b the byte to add
     */
    public void addToMatch(byte b) {
        if(!currentMatch.isFull()) {
            currentMatch.add(b);
        }
    }

    /**
     * Returns a worklist representing the current match.  Clients of this tree
     * SHOULD NOT be able to modify this.currentMatch by modifying the returned
     * worklist.  So, this method should return a deep copy.
     *
     * @return a copy of the current match
     */
    public FIFOWorkList<Byte> getMatch() {
        FIFOWorkList<Byte> result = new CircularArrayFIFOQueue<Byte>(maxMatchLength);
        for(Byte b: currentMatch) {
            result.add(b);
        }
        return result;
    }

    /**
     * Returns the distance from the end of the current match to some leaf
     *
     * @return the number of (non-terminator) characters between lastMatch and the
     *         closest leaf on an arbitrary path
     */
    public int getDistanceToLeaf() {
        int distance = 0;
        HashTrieNode current = lastMatch;
        while(!current.pointers.containsKey(TERMINATOR)) {
            current = current.pointers.values().iterator().next();

            distance++;
        }
        return distance;
    }

    /**
     * Advances this trie by the found match.
     *
     * For every byte b in match, you should do the following:
     *
     * 1. If the contents of the suffixtrie are at full capacity,
     *    shift off a byte and remove the whole word from the trie
     * 2. Append b to the end of every stored node
     * 3. Re-insert the empty string back into the trie
     *
     * HINT: be sure to pay careful attention to how exactly you are updating
     * your various fields, and how exactly they interact with one another. See the
     * example and descriptions in the spec for more details about this method.
     */
    public void advance() {
        if (currentMatch.size() >= size && size > maxMatchLength) {
            contents.clear();
            for (int i = 0; i < size; i++) {
                contents.add(currentMatch.peek(currentMatch.size() - (size - i)));
            }
            currentMatch.clear();
            for (Byte content: contents) {
                currentMatch.add(content);
            }
            contents.clear();
            advance();
        } else {
            clear();
            while (currentMatch.hasWork()) {
                if(contents.isFull()) {
                    ByteString newContents = new ByteString(contents);
                    delete(newContents);
                    contents.next();
                }
                Byte b = currentMatch.next();
                contents.add(b);
                FIFOWorkList<HashTrieNode> newLeaves = new ListFIFOQueue<HashTrieNode>();
                for (HashTrieNode leaf: leaves) {
                    leaf.pointers.clear();
                    leaf.pointers.put(b, new HashTrieNode());
                    leaf = leaf.pointers.get(b);
                    leaf.pointers.put(TERMINATOR, new HashTrieNode(true));
                    newLeaves.add(leaf);
                }
            }
        }
    }

    /**
     * Clears the state of this trie to identical to initialization.
     */
    @Override
    public void clear() {
        root = new HashTrieNode();
        HashTrieNode hashTrieNode = (HashTrieNode)this.root;
        lastMatch = hashTrieNode;
        lastMatch.pointers.put(TERMINATOR, new HashTrieNode(true));
        currentMatch = new CircularArrayFIFOQueue<Byte>(maxMatchLength);
        contents = new CircularArrayFIFOQueue<Byte>(size);
        leaves = new ListFIFOQueue<HashTrieNode>();
        leaves.add(lastMatch);
    }
}
