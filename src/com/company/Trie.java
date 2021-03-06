package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Trie {

    Trie(String address) {
        try {
            Scanner scanner = new Scanner(new File(address));

            while (scanner.hasNext())
                this.insert(scanner.nextLine());

        } catch (FileNotFoundException e) {
            System.out.println("Invalid Address!");
        }
    }

    static class TrieNode {
        char value;
        TrieNode[] children = new TrieNode[26]; // 26 is the number of alphabets (possibilities)
        boolean isFullWord;
        int frequency;

        public TrieNode(char value) {
            this.value = value;
            this.isFullWord = false;
            this.frequency = 0;
        }
    }

    private final TrieNode root = new TrieNode(' ');

    private void insert(String str) {
        TrieNode current = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 97;

            if (current.children[index] == null)
                current.children[index] = new TrieNode(str.charAt(i));

            current = current.children[index];
        }
        current.isFullWord = true; // Confirm the placement of a word for further usages
    }

    public String[] findWords(String str) {
        TrieNode node = lastNode(str);
        PriorityQueue words = new PriorityQueue();

        if (node != null)
            suggestionWords(node, str, words);

        String[] suggestion = new String[3];
        for (int i = 0; i < suggestion.length; i++)
            suggestion[i] = words.remove();
        return suggestion;
    }

    private void suggestionWords(TrieNode root, String str, PriorityQueue words) {
        if (root.isFullWord)
            words.insert(str, root.frequency);

        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null)
                suggestionWords(root.children[i], str + root.children[i].value, words);
        }
    }

    private TrieNode lastNode(String str) {
        TrieNode current = root;
        for (int i = 0; i < str.length(); i++) {
            int index = str.charAt(i) - 97;
            TrieNode child = current.children[index];
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }

    public void updateFrequency(String str){
        TrieNode last = lastNode(str);

        if (last != null)
            last.frequency++;
    }
}
