package glut.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树的节点
 * 
 * @author leo
 * 		
 */
public class TrieTreeNode {
	/**
	 * 节点深度
	 */
	public short						depth;
	/**
	 * 存放当前节点的所有子节点
	 */
	public Map<Integer, TrieTreeNode>	children	= new HashMap<Integer, TrieTreeNode>();
	/**
	 * 是否为单词的结尾
	 */
	public boolean						isTail		= false;
	/**
	 * 双亲节点
	 */
	public TrieTreeNode					parent;
	/**
	 * 可以是a-z中的任意字母
	 */
	public char							value;
	/**
	 * 当单词相同时,wordCount++,用于计算相同的单词个数
	 */
	public int							wordCount	= 0;
	/**
	 * 存放一整个单词
	 */
	public StringBuilder				word		= new StringBuilder();
	
	public TrieTreeNode() {
		// TODO Auto-generated constructor stub
	}
	
}
