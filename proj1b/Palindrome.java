/** A class for palindrome operations. */
public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        if (word.length() >= 2) {
            Deque<Character> deque = wordToDeque(word);
            while (deque.size() > 1) {
                char first = deque.removeFirst();
                char last = deque.removeLast();
                if (first != last) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() >= 2) {
            Deque<Character> deque = wordToDeque(word);
            while (deque.size() > 1) {
                char first = deque.removeFirst();
                char last = deque.removeLast();
                if (!cc.equalChars(first, last)) {
                    return false;
                }
            }
        }
        return true;
    }
}
