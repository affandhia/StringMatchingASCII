import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class SMASCII {
    private HashMap<Integer, AlphabetDatabase> database;
    private int totalIter = 0;

    public SMASCII() {
        this.database = new HashMap<>();
    }

    public void add(String username) {
        username = username.toLowerCase();

        AlphabetDatabase db = this.getAlphabetDatabase(username);
        Alphabet alphabetDb = this.getAlphabetContainer(username, db);

        // add username to its database
        LinkedList<String> stringDbList = this.getAlphabetList(alphabetDb);

        Iterator<String> iterator = stringDbList.iterator();
        int i = 0;

        while (iterator.hasNext()) {
            String next = iterator.next();
            if (username.compareTo(next) > 0) {
                break;
            } else if (username.compareTo(next) == 0) {
                return;
            }
            i++;
        }

        stringDbList.add(i, username);

        alphabetDb.setCount(alphabetDb.getCount() + 1);
    }

    public boolean find(String text) {
        this.totalIter = 0;
        text = text.toLowerCase();

        AlphabetDatabase db = this.getAlphabetDatabase(text);
        Alphabet alphabetDb = this.getAlphabetContainer(text, db);

        // add text to its database
        LinkedList<String> stringDbList = this.getAlphabetList(text);

        Iterator<String> iterator = stringDbList.iterator();

        while (iterator.hasNext()) {
            String next = iterator.next();

            if (this.checkString(text, next)) return true;
            this.totalIter += 1;
        }

        return false;
    }

    private boolean checkString(String first, String second) {
        if (first.length() != second.length()) return false;


        int fIdx = 0;
        int lIdx = first.length() - 1;

        if (first.charAt(lIdx) != second.charAt(lIdx)) return false;

        int mIdx;
        int nIdx;

        if (lIdx - fIdx == 1) return true;
        else if (lIdx - fIdx == 2) return first.charAt(1) == second.charAt(1);
        else if (lIdx - fIdx == 3) return first.charAt(1) == second.charAt(1) && first.charAt(2) == second.charAt(2);
        else {
            final int[] ints = new java.util.Random().ints(fIdx + 1, lIdx).distinct().limit(2).toArray();
            mIdx = ints[0];
            nIdx = ints[1];
        }

        for (int i = fIdx + 1; i < lIdx; i++) {
            if (i == mIdx || i == nIdx) continue;
            if (first.charAt(i) != second.charAt(i)) return false;
        }

        return true;
    }


    private AlphabetDatabase getAlphabetDatabase(String text) {
        // get sum value of string ascii value
        int index = this.asciiValueSum(text);

        // get the alphabet database based on index
        AlphabetDatabase db = this.database.get(index);
        // create alphabet Database if not found
        if (db == null) {
            db = new AlphabetDatabase();
            this.database.put(index, db);
        }

        return db;

    }

    private Alphabet getAlphabetContainer(String text, AlphabetDatabase db) {
        return db.getDatabase().get(text.charAt(0));
    }

    private LinkedList<String> getAlphabetList(String text) {
        // get the alphabet database based on index and pass to helper function
        AlphabetDatabase db = this.getAlphabetDatabase(text);

        return this.getAlphabetList(text, db);
    }

    private LinkedList<String> getAlphabetList(String text, AlphabetDatabase db) {
        // get the considered alphabet database refer to first letter of text
        Alphabet alphabetDb = this.getAlphabetContainer(text, db);

        return getAlphabetList(alphabetDb);
    }

    private LinkedList<String> getAlphabetList(Alphabet alphabetDb) {
        // get its string container database from selected alphabet database
        StringDatabase stringDb = alphabetDb.getDatabase();

        return stringDb.getDatabase();
    }

    private int asciiValueSum(String text) {
        int sum = 0;
        for (int i = 0; i < text.length(); i++) {
            sum += text.charAt(i);
        }

        return sum;
    }

    public HashMap<Integer, AlphabetDatabase> getDatabase() {
        return database;
    }

    public void setDatabase(HashMap<Integer, AlphabetDatabase> database) {
        this.database = database;
    }
}

class Alphabet {
    private char letter;
    private int count;
    private StringDatabase database;

    Alphabet(char letter) {
        this.letter = letter;
        this.count = 0;
        this.database = new StringDatabase();
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public StringDatabase getDatabase() {
        return database;
    }

    public void setDatabase(StringDatabase database) {
        this.database = database;
    }
}

class AlphabetDatabase {
    private HashMap<Character, Alphabet> database;

    public AlphabetDatabase() {
        this.database = new HashMap<>();

        this.populateDatabase();
    }


    public HashMap<Character, Alphabet> getDatabase() {
        return database;
    }

    public void setDatabase(HashMap<Character, Alphabet> database) {
        this.database = database;
    }

    private void populateDatabase() {
        int firstLetter = 'a';
        int lastLetter = 'z';

        for (int i = firstLetter; i <= lastLetter; i++) {
            this.database.put((char) i, new Alphabet((char) i));
        }
    }

}

class StringDatabase {
    private LinkedList<String> database;

    StringDatabase() {
        this.database = new LinkedList<>();
    }

    public LinkedList<String> getDatabase() {
        return database;
    }

    public void setDatabase(LinkedList<String> database) {
        this.database = database;
    }
}