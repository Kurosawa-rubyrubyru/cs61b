public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new ArrayDeque<>();
        for (int index = 0; index < word.length(); index += 1) {
            char c = word.charAt(index);
            d.addLast(c);
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        return checkisPalindrome(d);

    }

    private boolean checkisPalindrome(Deque<Character> d) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        } else {
            if (d.removeFirst() == d.removeLast()) {
                return checkisPalindrome(d);
            } else {
                return false;
            }
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        return checkisPalindrome(d, cc);
    }

    private boolean checkisPalindrome(Deque<Character> d, CharacterComparator cc) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        } else {
            if (cc.equalChars(d.removeFirst(), d.removeLast())) {
                return checkisPalindrome(d, cc);
            } else {
                return false;
            }
        }
    }
}
