package personal.leo.datastructure.trie;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Leo
 * @Blog: http://blog.csdn.net/lc0817
 * @CreateTime: 2017/3/30 23:09
 * @Description:
 */
@Setter
@Getter
public class Trie implements Serializable {
    private static final long serialVersionUID = 7368314038181488490L;

    private transient final int DEFAULT_CHILDREN_COUNT = 32;
    /**
     * 记录总词数
     */
    private int wordCount;
    private TrieNode root;
    private boolean replaceOldExtra;
    /**
     * 用于字典树扩展,方便使用者在特定的地方,进行一些业务需要的回调.
     */
    private TrieCallback trieCallback;

    @Builder
    public Trie(int wordCount, boolean replaceOldExtra, TrieCallback trieCallback) {
        this.wordCount = wordCount;
        this.root = new TrieNode();
        this.replaceOldExtra = replaceOldExtra;
        this.trieCallback = trieCallback;
    }

    public boolean startWith(String str) {
        return startWith(str, false);
    }

    public boolean exactMatch(String str) {
        return startWith(str, true);
    }

    private boolean startWith(String str, boolean exactMatch) {
        if (StringUtils.isNotBlank(str)) {
            char[] chars = str.toCharArray();
            TrieNode parent = root;
            Map<Character, TrieNode> children;
            TrieNode child;
            for (int i = 0; i < chars.length; i++) {
                children = parent.getChildren();
                if (children == null) {
                    return false;
                } else {
                    char c = chars[i];
                    child = children.get(c);
                    if (child == null) {
                        return false;
                    } else {
                        parent = child;
                    }
                }
            }
            if (exactMatch) {
                if (parent.isTail()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void put(String str) {
        put(str, null, true);
    }

    public void put(String str, Object extra) {
        put(str, extra, true);
    }

    public void put(String str, Object extra, boolean trim) {
        if (StringUtils.isNotBlank(str)) {
            if (trim) {
                str = str.trim();
            }
            char[] chars = str.toCharArray();
            TrieNode parent = root;
            Map<Character, TrieNode> children;
            TrieNode child;
            for (int i = 0; i < chars.length; i++) {
                children = parent.getChildren();
                if (children == null) {
                    children = new HashMap<>(DEFAULT_CHILDREN_COUNT);
                    parent.setChildren(children);
                }
                char c = chars[i];
                child = children.get(c);
                boolean childIsNull = false;
                if (child == null) {
                    childIsNull = true;
                    child = new TrieNode()
                            .setDepth(i + 1)
                            .setParent(parent)
                            .setValue(c);
                    children.put(c, child);
                }
                if (chars.length == i + 1) {
                    child.setTail(true);
                    child.wordCountIncrement();
                    if (childIsNull) {//如果已经遍历到最后一个字符,并且该子节点为null,则代表这是一个新单词,这时候才会进行wordCount++
                        wordCount++;
                    }
                    if (extra != null) {
                        if (child.getExtra() == null) {
                            child.setExtra(extra);
                        } else {
                            if (replaceOldExtra) {
                                child.setExtra(extra);
                            }
                        }
                    }
                    if (null != trieCallback) {
                        trieCallback.afterPutting(this, child);
                    }
                }
                parent = child;
            }
        }
    }

    public TrieNode get(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            char[] chars = str.toCharArray();
            TrieNode parent = root;
            Map<Character, TrieNode> children;
            TrieNode child;
            for (int i = 0; i < chars.length; i++) {
                children = parent.getChildren();
                if (children == null) {
                    return null;
                } else {
                    char c = chars[i];
                    child = children.get(c);
                    if (child == null) {
                        return null;
                    } else {
                        if (chars.length == i + 1) {
                            return child;
                        } else {
                            parent = child;
                        }
                    }
                }
            }
            return null;
        }
    }

}
