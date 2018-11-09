package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		TrieNode rootNode = new TrieNode(null,null,null);
		for (int i = 0; i< allWords.length; i++) 
		{
			insertLoc(rootNode, i, allWords, -1);
		}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return rootNode;
	}
	public static void insertLoc(TrieNode currNode, int wordInsert, String [] wordList, int currDepth)
	{
		if (currDepth == -1)
		{
			if (currNode.firstChild == null)
			{
				Indexes sub = new Indexes(wordInsert,(short) (0),(short) (wordList[wordInsert].length()-1));
				currNode.firstChild = new TrieNode(sub,null,null);
			}
			else
			{
				insertLoc(currNode.firstChild, wordInsert, wordList, currDepth+1);
			}
		}
		else if (wordList[currNode.substr.wordIndex].substring(currNode.substr.startIndex, currNode.substr.endIndex+1).charAt(0)
				== wordList[wordInsert].substring(currNode.substr.startIndex, wordList[wordInsert].length()).charAt(0))
		{
			currDepth = currNode.substr.startIndex;
			for (int i = currDepth; i < currNode.substr.endIndex+1; i++)
			{
				if (wordList[currNode.substr.wordIndex].charAt(i)
						!= wordList[wordInsert].charAt(i))
				{
					//currNode.substr.endIndex = (short) (i-1);
					if (currNode.firstChild == null)
					{
						Indexes sub = new Indexes(currNode.substr.wordIndex,(short) i,currNode.substr.endIndex);
						currNode.substr.endIndex = (short) (i-1);
						currNode.firstChild= new TrieNode(sub,null,null);
						insertLoc(currNode.firstChild, wordInsert, wordList, currNode.substr.endIndex+1);
					}
					else
					{
						insertLoc(currNode.firstChild, wordInsert, wordList, currNode.substr.endIndex+1);
					}
					return;
				}
			}
			if (currNode.firstChild != null)
			{
				insertLoc(currNode.firstChild, wordInsert, wordList, currNode.substr.endIndex+1);
			}
			//TrieNode child = insertLoc(currNode.firstChild, word, wordList);
		}
		else if (currNode.sibling == null)
		{
			Indexes sub = new Indexes(wordInsert,(short) (currDepth),(short) (wordList[wordInsert].length()-1));
			currNode.sibling = new TrieNode(sub,null,null);		
		}
		else if (currNode.sibling != null)
		{
			insertLoc(currNode.sibling,wordInsert,wordList,currDepth);
		}
	}
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned   nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	
	//public static ArrayList<TrieNode> completionList(TrieNode root,)
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		ArrayList<TrieNode> wordList = new ArrayList<TrieNode>();
		if (root.firstChild == null && root.substr == null)
		{
			return wordList;
		}
		else if (root.substr == null)
		{
			wordList = completionList(root.firstChild,allWords,prefix);
		}
		else if (allWords[root.substr.wordIndex]
				.charAt(root.substr.startIndex)
				== prefix.charAt(root.substr.startIndex))
		{
			int end = prefix.length();
			if (end > root.substr.endIndex)
				end = root.substr.endIndex+1;
			for (int i = root.substr.startIndex; i < end; i++)
			{
				if (allWords[root.substr.wordIndex].charAt(i)
						!= prefix.charAt(i))
				{
					return wordList;
				}
			}
			if (root.firstChild != null)
			{
				if (end == prefix.length())
					wordList = insertAll(root.firstChild, wordList);
				else //if(root.sibling != null)
					wordList = completionList(root.firstChild,allWords,prefix);
//					wordList = insertAll(root.firstChild,wordList);
			}
			else
			{
				wordList.add(root);
				return wordList;
			}
		}
		else if (root.sibling != null)
		{
			wordList = completionList(root.sibling,allWords,prefix);
		}
		else
		{
		//	wordList = completionList(root.firstChild, allWords, prefix);
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		return wordList;
	}
	public static ArrayList<TrieNode> insertAll(TrieNode root, ArrayList<TrieNode> wordList)
	{
		if(root.firstChild != null)
		{
			wordList = insertAll(root.firstChild,wordList);
		}
		else
		{
			wordList.add(root);
		}
		if(root.sibling != null)
		{
			wordList = insertAll(root.sibling,wordList);
		}
		return wordList;
	}
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
