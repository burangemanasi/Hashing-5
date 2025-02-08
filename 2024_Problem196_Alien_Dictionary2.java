//269. Alien Dictionary - https://leetcode.com/problems/alien-dictionary/description/
/*
There is a new alien language that uses the English alphabet. However, the order of the letters is unknown to you.

You are given a list of strings words from the alien language's dictionary. Now it is claimed that the strings in words are
sorted lexicographically
 by the rules of this new language.

If this claim is incorrect, and the given arrangement of string in words cannot correspond to any order of letters, return "".

Otherwise, return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there are multiple solutions, return any of them.



Example 1:

Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
 */
//Time Complexity: O(V+E) + O(n*l)
//Space Complexity: O(1) ~ 26 characters are stored in Map and indegrees array
class Solution {
    HashMap<Character, HashSet<Character>> map; //adjacency list
    int[] indegrees; //incoming edge or dependent vertex array
    public String alienOrder(String[] words) {
        this.map = new HashMap<>();
        this.indegrees = new int[26];

        buildGraph(words);
        //in ["apple","app"] case
        if(map.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        //add independent nodes
        //go over map keyset, if indegrees is 0 -> add to queue
        Queue<Character> q = new LinkedList<>();
        for(char key : map.keySet()){
            if(indegrees[key - 'a'] == 0){
                q.add(key);
                //add to result
                sb.append(key);
            }
        }
        while(!q.isEmpty()){
            char curr = q.poll();
            for(char c: map.get(curr)){
                indegrees[c-'a']--;
                if(indegrees[c-'a'] == 0){
                    q.add(c);
                    sb.append(c);
                }
            }
        }
        //to check cycle, check for lengths
        if(sb.length() != map.size()){
            return "";
        }
        return sb.toString();
    }

    private void buildGraph(String[] words){
        for(String word : words){
            for(char c : word.toCharArray()){
                map.put(c, new HashSet<>());
            }
        }
        //compare 2 words
        for(int i=0; i<words.length-1; i++){
            String first = words[i];
            String second = words[i+1];
            //edge case
            if(first.startsWith(second) && first.length() > second.length()){ //case: ["apple","app"]
                map.clear();
                return;
            }
            //compare 1st characters of 2 words
            for(int j=0; j<first.length() && j<second.length(); j++){
                char fChar = first.charAt(j);
                char sChar = second.charAt(j);
                if(fChar != sChar){
                    HashSet<Character> set = map.get(fChar); //for independent char, get from map and add dependent
                    if(!set.contains(sChar)){
                        set.add(sChar);
                        indegrees[sChar - 'a']++;
                    }
                    break;
                }
            }
        }
    }
}