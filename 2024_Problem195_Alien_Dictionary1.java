//953. Verifying an Alien Dictionary - https://leetcode.com/problems/verifying-an-alien-dictionary/description/
//Time Complexity: O(n*l) ~ 'n' is no. of input words and 'l' is length of each word
//Space Complexity: O(1) ~ 26 characters

class Solution {
    HashMap<Character, Integer> map;
    public boolean isAlienSorted(String[] words, String order) {
        this.map = new HashMap<>();
        //create map for positions of the dictionary characters
        for(int i=0; i<order.length(); i++){
            char c = order.charAt(i);
            map.put(c, i);
        }
        //comparing words in pair thus, words.length-1
        for(int i=0; i<words.length-1; i++){
            String first = words[i];
            String second = words[i+1];

            if(notSorted(first, second)) return false;
        }
        return true;
    }

    //checking for compliment
    private boolean notSorted(String first, String second){
        for(int i=0; i<first.length() && i<second.length(); i++){
            char fChar = first.charAt(i);
            char sChar = second.charAt(i);
            if(fChar != sChar){
                return map.get(fChar) > map.get(sChar);
            }
        }
        return first.length() > second.length();
    }
}

