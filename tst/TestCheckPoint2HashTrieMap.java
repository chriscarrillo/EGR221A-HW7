import datastructures.dictionaries.HashTrieMap;
import egr221a.types.AlphabeticString;
import org.junit.Assert;
import org.junit.Test;

/**
 * Feel free to add more tests!
 * @author Chris Nugent
 */
public class TestCheckPoint2HashTrieMap {

    private AlphabeticString convert(String s) {
        return new AlphabeticString(s);
    }

    @Test
    public void insertTest() {
        HashTrieMap<Character, AlphabeticString, String> map = new HashTrieMap(AlphabeticString.class);
        map.insert(convert("apple"), "banana");
        map.insert(convert("orange"), "orange");
        map.insert(convert("lemons"), "limes");

        Assert.assertEquals("banana", map.find(convert("apple")));
        Assert.assertEquals("orange", map.find(convert("orange")));
        Assert.assertEquals("limes", map.find(convert("lemons")));
    }
}