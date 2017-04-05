# Project 1 (Zip) Write-Up #
--------

#### How Was Your Partnership? ####
-   Did both partners do an equal amount of work?  If not, why not?
    What happened?<pre>
Yes. We both devoted the same amount of work into this
project. We worked on every class together.
</pre><br>

-----

#### Project Enjoyment ####
-   What was your favorite part of the project?  What was your least
    favorite part of the project?<pre>
Our favorite part of the project was learning about WorkLists and
implementing our own generic data structures. Our least favorite
part was trying to figure out the HashTrieMap. After visiting
Dr. Han in her office though, things got better. Thank you!
</pre><br>

-   Did you enjoy the project?<pre>
We enjoyed the project a lot. It had a lot of good review in it.
On top of that, we feel like we learned a lot from it and working
with others.
</pre><br>

-----

#### WorkLists, Tries, and Zip ####
-   The ADT for a WorkList explicitly forbids access to the middle elements.  However, the FixedSizeFIFOWorkList has a peek(i) method
    which allows you to do exactly that.  Why is this an acceptable addition to the WorkList ADT in this particular case but not in general?<pre>
This is an acceptable addition because in the
FixedSizeFIFOWorkList, there is a fixed size. It will
allow you to iterate through the array dependent on
the index parameter of the peek method. Whereas a 
normal WorkList does not have a fixed size, so you
cannot access the middle element.
</pre><br>
-   As we've described it, a `TrieMap` seems like a general-purpose replacement for `HashMap` or `TreeMap`.  Why might we still want to use one
    of these other data structures instead?<pre>
We would want to use a HashMap or a TreeMap when the parent
nodes will only have a maximum of two children. A TrieMap
is used when a parent node has more than two children, such
as the case of this project. We might want to use HashMap
or TreeMap because they are more generic than TrieMaps and
can work on any object that is hashable. TrieMaps work
on a sequence.
</pre><br>
-   One of the applications of Tries is in solving Word Searches.  A "word search" is an n x m rectangle of letters.  The goal is to find all
    of the possible words (horizontal, vertical, diagonal, etc.).  In Boggle, a similar game, any consecutive chain of letters (even repetitions)
    are allowed.  Explain (in very high-level psuedo-code) how you might solve this problem with a TrieSet or a TrieMap.  Make sure to detail
    how a similar solution that uses a HashSet/HashMap instead would be different and why using a Trie might make the solution better.<pre>
public Node getNode(String s) {
    Node root = this;
    for (length of s) {
        int index = index of ith character in alphabet;
        Node child = children of root;
        if (child == null) {
            return null;
        }
        root = child;
    }
    return root;
}
</pre><br>
-   One of the classes in the egr221a.main package is called Zip.  This class uses your PriorityQueue to do Huffman coding, your FIFOQueue as a buffer,
    your stack to calculate the keyset of a trie (using recursive backtracking), and your SuffixTrie to do LZ77Compression.  Find some text file
    (a free book from https://www.gutenberg.org/ or even the HTML of some website) and use Zip.java to zip it into a zip file.  Then, use a 
    standard zip utility on your machine (Finder on OS X, zip on Linux, WinZip or the like on Windows) to UNZIP your file.  Check that you got back
    the original.  Congratulations!  Your program correctly implements the same compression algorithm you have been using for years!  Discuss in a
    sentence or two how good the compression was and why you think it was good or bad.<pre>
We decided to zip a .html page. After unzipping the compressed zip, it was
the same size as the original .html. The compression was right-on accurate.
We think this is good because the original size of the .html file was
443 KB. When zipped, it was 225 KB. When unzipped, it was back to 443 KB.
When we tried the provided test.txt, we did a DiffCheck and the files were
the same.
</pre><br>
-   Now that you've played with Zip, we want you to do an **experiment** with Zip.  Notice that there is a constant called `BUFFER_LENGTH` in `Zip.java`.
    Higher values of this constant makes the compression algorithm that Zip uses use more memory and consequently more time.  The "compression ratio"
    of a file is the uncompressed size divided by the compressed size.  Compare time, space, type of input file, and compression ratio by running
    your code on various inputs.  We would like an in-depth analysis.  You should try at least one "book-like" file, at least one "website-like" file,
    and some other input of your choice.  We expect you to draw meaningful conclusions and possibly have graphs that convince us of your conclusions.
    This single question is worth almost as much as the implementation of `ArrayStack`; so, please take it seriously.  If you spend less than 20 minutes
    on this question, there is no conceivable way that you answered this question in the way we were intending.<pre>
**TODO**: Answer this question
</pre><br>

#### Above and Beyond ####
-   Did you do any Above and Beyond?  Describe exactly what you
    implemented.<pre>
**TODO**: Answer this question
</pre><br>
