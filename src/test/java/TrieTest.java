import org.junit.Before;
import org.junit.Test;
import personal.leo.datastructure.trie.Trie;

import java.io.*;
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
        int SIZE = 10000;
        System.out.println("数据总量:" + SIZE);
        List<String> uuidList = new ArrayList<>(SIZE);
        String uuid, tmpUuid;
        uuid = UUID.randomUUID().toString();
        System.out.println("单条数据示例:" + uuid);
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
        System.out.println("Map  插入总耗时(s):" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        begin = System.nanoTime();
        for (int i = 0; i < SIZE; i++) {
            String tmp = uuidList.get(i);
            trie.put(tmp, tmp);
        }
        System.out.println("Trie 插入总耗时(s):" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        begin = System.nanoTime();
        String s = map.get(tmpUuid);
        System.out.println("Map  查询总耗时(s):" + BigDecimal.valueOf(System.nanoTime() - begin, 9));
        begin = System.nanoTime();
        Object extra = trie.get(tmpUuid).getExtra();
        System.out.println("Trie 查询总耗时(s):" + BigDecimal.valueOf(System.nanoTime() - begin, 9));

        ByteArrayOutputStream baos0 = new ByteArrayOutputStream();
        ObjectOutputStream oos0 = new ObjectOutputStream(baos0);
        oos0.writeObject(map);
        oos0.flush();
        System.out.println("Map  占用内存(MB):" + baos0.size() / 1024.0 / 1024.0);


        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ObjectOutputStream oos1 = new ObjectOutputStream(baos1);
        oos1.writeObject(trie);
        oos1.flush();
        System.out.println("Trie 占用内存(MB):" + baos1.size() / 1024.0 / 1024.0);


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
