package personal.leo.datastructure.trie;

/**
 * @Author: Leo
 * @Blog: http://blog.csdn.net/lc0817
 * @CreateTime: 2017/3/31 10:03
 * @Description:
 */
public interface TrieCallback {
    void afterPutting(Trie trie, TrieNode trieNode);
}
