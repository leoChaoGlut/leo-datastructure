package personal.leo.datastructure.trie;

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

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
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
        put(str, true);
    }

    public void put(String str, boolean trim) {
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
                if (child == null) {
                    child = new TrieNode()
                            .setDepth(i + 1)
                            .setParent(parent)
                            .setValue(c);
                    children.put(c, child);
                }
                if (chars.length == i + 1) {
                    child.setTail(true)
                            .wordCountIncrement();
                }
                parent = child;
            }
        }
    }
}
