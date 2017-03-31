package personal.leo.datastructure.trie;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: Leo
 * @Blog: http://blog.csdn.net/lc0817
 * @CreateTime: 2017/3/30 22:56
 * @Description: 非线程安全
 */
@Getter
@Setter
public class TrieNode implements Serializable {
    private static final long serialVersionUID = -6433966647500518707L;
    /**
     * 节点深度
     */
    private int depth;
    /**
     * 是否为单词的结尾
     */
    private boolean tail;
    private char value;
    /**
     * 存放额外的数据
     */
    private Object extra;
    /**
     * 相同单词的总数,仅当单词结尾才+1
     */
    private int wordCount;
    private TrieNode parent;
    /**
     * key: char
     * value: {@link TrieNode}
     */
    private Map<Character, TrieNode> children;

    public int wordCountIncrement() {
        return ++wordCount;
    }
}
