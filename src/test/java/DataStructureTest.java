import org.junit.Test;
import personal.leo.datastructure.trie.Trie;

/**
 * @Author: Leo
 * @Blog: http://blog.csdn.net/lc0817
 * @CreateTime: 2017/3/30 23:00
 * @Description:
 */
public class DataStructureTest {

    @Test
    public void trieTest() {
        Trie trie = new Trie();
        trie.put("abcde");
        trie.put("abc");
        trie.put("abd");
        trie.put("ab");
        trie.put("b");
        trie.put("a");

        System.out.println(trie.startWith("ab"));
        System.out.println(trie.exactMatch("abcd"));
    }
}
