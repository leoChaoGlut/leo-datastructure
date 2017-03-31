import org.junit.Before;
import org.junit.Test;
import personal.leo.datastructure.trie.Trie;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.util.*;

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
//                .trieCallback(new TrieCallback() {
//                    @Override
//                    public void afterPutting(Trie trie, TrieNode trieNode) {
//                        System.out.println(trieNode.getExtra());
//                    }
//                })
                .build();
//        trie.put("111111111111", "t0");
//        trie.put("111111111112", "t0");
//        trie.put("111111111113", "t0");
//        trie.put("111111111114", "t2");
//        trie.put("111111111114", "t2");

    }

    @Test
    public void effeciencyTest() {

    }

    @Test
    public void putTest() throws Exception {
        Map<String, String> map = new HashMap<>(10240);
        int SIZE = 200000;
        List<String> uuidList = new ArrayList<>(SIZE);
        String uuid, tmpUuid;
        uuid = UUID.randomUUID().toString();
        tmpUuid = uuid;
        for (int i = 0; i < SIZE; i++) {
            uuidList.add(uuid);
            uuid = UUID.randomUUID().toString();
        }

        long begin = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            String tmp = uuidList.get(i);
            map.put(tmp, tmp);
        }
        System.out.println("Map  插入总耗时:" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        begin = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            String tmp = uuidList.get(i);
            trie.put(tmp, tmp);
        }
        System.out.println("Trie 插入总耗时:" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        begin = System.nanoTime();
        String s = map.get(tmpUuid);
        System.out.println("Map  查询总耗时:" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        begin = System.nanoTime();
        Object extra = trie.get(tmpUuid).getExtra();
        System.out.println("Trie 查询总耗时:" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        System.out.println(s);
        System.out.println(extra);
//        ObjectOutputStream oos0 = new ObjectOutputStream(new FileOutputStream("D:\\1.txt"));
//        oos0.writeObject(trie);
//        oos0.flush();
//        oos0.close();
//
//        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("D:\\2.txt"));
//        oos1.writeObject(map);
//        oos1.flush();
//        oos1.close();
//

    }

    @Test
    public void read() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\1.txt"));
        trie = (Trie) ois.readObject();
        int wordCount = trie.getWordCount();
        System.out.println(wordCount);
    }

    @Test
    public void getTest() {
        System.out.println(trie.getWordCount());
        System.out.println(trie.get("111111111111").getExtra());
        System.out.println(trie.get("111111111114").getExtra());
    }
}
