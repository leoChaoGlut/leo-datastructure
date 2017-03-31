import org.junit.Before;
import org.junit.Test;
import personal.leo.datastructure.trie.Trie;
import personal.leo.datastructure.trie.TrieCallback;
import personal.leo.datastructure.trie.TrieNode;

/**
 * @Author: Leo
 * @Blog: http://blog.csdn.net/lc0817
 * @CreateTime: 2017/3/30 23:00
 * @Description:
 */
public class TrieTest {

    Trie trie;

    @Before
    public void before() {
        trie = Trie.builder()
                .replaceOldExtra(true)
                .trieCallback(new TrieCallback() {
                    @Override
                    public void afterPutting(Trie trie, TrieNode trieNode) {
                        System.out.println(trieNode.getExtra());
                    }
                })
                .build();
        trie.put("111111111111", "t0");
        trie.put("111111111112", "t0");
        trie.put("111111111113", "t0");
        trie.put("111111111114", "t2");
        trie.put("111111111114", "t2");

    }

    @Test
    public void getTest() {
        System.out.println(trie.getWordCount());
        System.out.println(trie.get("111111111111").getExtra());
        System.out.println(trie.get("111111111114").getExtra());
    }
}
