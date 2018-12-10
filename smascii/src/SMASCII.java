import java.util.*;

public class SMASCII {
    private HashMap<Integer, AlphabetDatabase> database;

    public SMASCII() {
        this.database = new HashMap<>();
    }

    public void add(String username) {
        // get sum value of string ascii value
        int index = this.asciiValueSum(username);

        // get the alphabet database based on index
        AlphabetDatabase db = this.database.get(index);

        // create alphabet Database
        if (db == null) {
            db = new AlphabetDatabase();
            this.database.put(index, db);
        }

        Alphabet alphabetDb = db.getDatabase().get(username.charAt(0));

        StringDatabase stringDb = alphabetDb.getDatabase();

        stringDb.getDatabase().add(username);

        alphabetDb.setCount(alphabetDb.getCount() + 1);
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
    private LinkedList<Alphabet> database;

    public AlphabetDatabase(){
        this.database = new LinkedList<>();

        this.populateDatabase();
    }

    public LinkedList<Alphabet> getDatabase() {
        return database;
    }

    public void setDatabase(LinkedList<Alphabet> database) {
        this.database = database;
    }

    private void populateDatabase() {
        int firstLetter = 'a';
        int lastLetter = 'z';

        for (int i = firstLetter; i <= lastLetter; i++) {
            this.database.add(new Alphabet((char) i));
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