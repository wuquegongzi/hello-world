package com.leon.leetcode.L804;

import java.util.TreeSet;

/**
 * 804. 唯一摩尔斯密码词
 */
public class UniqueMorseRepresentations {


    public int uniqueMorseRepresentations(String[] words) {

        String[] codes = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};

        TreeSet<String> set = new TreeSet<String>();

        for (String world:words) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < world.length(); i++) {

                sb.append(codes[world.charAt(i) - 'a']);
            }

            set.add(sb.toString());

        }

        return set.size();

    }

    public static void main(String[] args) {
        String[] words = {"gin", "zen", "gig", "msg"};

        UniqueMorseRepresentations uniqueMorseRepresentations = new UniqueMorseRepresentations();

        System.out.println(uniqueMorseRepresentations.uniqueMorseRepresentations(words));
    }

}
