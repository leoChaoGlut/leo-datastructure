package glut.func;

import glut.bean.TrieTreeNode;
import glut.bean.WordAndCount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 字典树构造器类
 * 
 * @author leo
 * 		
 */
public class TrieTreeMaker {
	private TrieTreeNode				root;
	/**
	 * StringBuilder 容量上限,可修改.
	 */
	private int							capacity		= 1024 * 1024 * 10;
	/**
	 * 正则规则,有需要的话可以改.
	 */
	private String						regex			= "[^a-zA-Z]+";
	/**
	 * 未排序的结果,可通过调用本类的sort方法进行排序
	 */
	private Map<String, WordAndCount>	wordCountMap	= new TreeMap<String, WordAndCount>();
	
	/**
	 * 无参构造器
	 */
	public TrieTreeMaker() {
		// TODO Auto-generated constructor stub
		root = createRoot();
		
	}
	
	/**
	 * 调用该方法,可以根据指定的文件的文件内容来构造字典树
	 * 
	 * @param srcFile
	 *            要被读取的文件,文件编码最好是UTF-8.
	 */
	public void obtainDataAndCreateTrieTree(File srcFile) {
		BufferedReader br = null;
		String data = "";
		long fileSize = srcFile.length();
		// 如果文件太大,则抛出异常
		if (fileSize > capacity) {
			try {
				throw new Exception("file is too big");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		// 给sb指定容量,避免扩容操作.
		StringBuilder sb = new StringBuilder((int) (fileSize + 1024));
		
		try {
			br = new BufferedReader(new FileReader(srcFile));
			
			while ((data = br.readLine()) != null) {
				sb.append(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		createTrieTree(sb.toString());
	}
	
	/**
	 * 通过给定的字符串创建字典树,暂时只支持英文
	 * 
	 * @param data
	 *            用于创建字典树的字符串,暂不支持中文.
	 */
	public void createTrieTree(String data) {
		
		// 分解出文本中的单词
		String[] strArr = data.split(regex);
		// 创建字典树的根节点
		
		// 将split后的str数组读入字典树
		for (String s : strArr) {
			// 单个的字母不考虑
			if (s.length() > 1) {
				// 每一个单词开始,都以root作为第一个双亲节点
				TrieTreeNode parent = root;
				// 开始循环每一个单词
				for (short i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					// 这里不考虑大写,只管小写.
					c = (char) (c < 'a' ? c + 32 : c);
					// 创建子节点
					TrieTreeNode child = createChildTrieTree(c, i, parent);
					int key = child.value * 100 + child.depth;
					parent.children.put(key, child);
					parent = child;
				}
				// 每一个单词循环完毕后,都将最后一个节点作为尾部.
				parent.isTail = true;
				// 每次遇到相同的单词都将wordCount++
				int wordCount = ++parent.wordCount;
				String word = parent.word.toString();
				// 将单词和单词出现次数放入map
				wordCountMap.put(word, new WordAndCount(word, wordCount));
			}
		}
	}
	
	/**
	 * 用于创建子节点
	 * 
	 * @param c
	 * @param depth
	 * @param parent
	 * @return
	 */
	private TrieTreeNode createChildTrieTree(char c, short depth, TrieTreeNode parent) {
		// 通过双亲节点,数的深度和字符c,3个条件判断是否已经存在该子节点,如果存在,直接获取.
		TrieTreeNode child = getChild(c, depth, parent);
		// 如果不存在,就新建一个,并将参数赋给它.
		if (child == null) {
			child = new TrieTreeNode();
			
			child.depth = depth;
			child.parent = parent;
			child.value = c;
			// 用于保存单词,通过isTail的配合,可以省去搜索单词的过程.
			child.word.append(parent.word).append(c);
		}
		
		return child;
	}
	
	/**
	 * 根据字符c,树深度和双亲节点,获取子节点.
	 * 
	 * @param c
	 * @param depth
	 * @param parent
	 * @return
	 */
	private TrieTreeNode getChild(char c, short depth, TrieTreeNode parent) {
		// TODO Auto-generated method stub
		// 每一个双亲节点,都有一个HashMap来存放所有的子节点,key是根据c和depth来合成的.
		Map<Integer, TrieTreeNode> children = parent.children;
		// 因为字典树的深度不超过26,所以十位和个位让depth来存放,百位以上让字符c来存放,这就可以构成一个独一无二的key来对应一个value
		int key = c * 100 + depth;
		return children.get(key);
	}
	
	/**
	 * 创建字典树的根节点
	 * 
	 * @return
	 */
	private TrieTreeNode createRoot() {
		TrieTreeNode root = new TrieTreeNode();
		// 因为单词的深度从0开始,而根节点不用于存放数据,所以深度设为-1.
		root.depth = -1;
		// 没爹没娘的孩子
		root.parent = null;
		// 随便给他一个值
		root.value = '0';
		
		return root;
	}
	
	/**
	 * 如果有需要,可利用已生成的wordCountMap来按单词出现次数进行排序,isAscent 用于控制升序降序
	 * 
	 * @param wordCountMap
	 *            已生成的wordCountMap
	 * @param isAscent
	 *            true:升序,false降序.
	 * @return 排序后的wordCountMap
	 */
	public List<Entry<String, WordAndCount>> sort(Map<String, WordAndCount> wordCountMap, boolean isAscent) {
		CustomComparator comparator = new CustomComparator(isAscent);
		// 先获取entrySet
		Set<Entry<String, WordAndCount>> entrySet = wordCountMap.entrySet();
		// 将entrySet放入List
		List<Entry<String, WordAndCount>> list = new ArrayList<Entry<String, WordAndCount>>(entrySet);
		
		Collections.sort(list, comparator);
		
		return list;
	}
	
	/**
	 * 内部比较自定义比较器
	 * 
	 * @author leo
	 * 		
	 */
	class CustomComparator implements Comparator<Entry<String, WordAndCount>> {
		public boolean isAscent = true;
		
		public CustomComparator() {
			// TODO Auto-generated constructor stub
		}
		
		public CustomComparator(boolean isAscent) {
			super();
			this.isAscent = isAscent;
		}
		
		public int compare(Entry<String, WordAndCount> o1, Entry<String, WordAndCount> o2) {
			// TODO Auto-generated method stub
			if (isAscent)
				return o2.getValue().count - o1.getValue().count;
			else
				return o1.getValue().count - o2.getValue().count;
		}
		
	}
	
	// *******getter,setter******
	public TrieTreeNode getRoot() {
		return root;
	}
	
	public void setRoot(TrieTreeNode root) {
		this.root = root;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public Map<String, WordAndCount> getWordCountMap() {
		return wordCountMap;
	}
	
	public void setWordCountMap(Map<String, WordAndCount> wordCountMap) {
		this.wordCountMap = wordCountMap;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public void setRegex(String regex) {
		this.regex = regex;
	}
}
