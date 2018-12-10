public class Main {
    public static void main(String[] args) {
        SMASCII item = new SMASCII();
        item.add("binaraga");
        item.add("binaragaa");
        item.add("binaragaa");

        KmpStringMatcher kmp = new KmpStringMatcher("binaragar");
        System.out.println(kmp.search("binaragaa"));

        System.out.println(NaiveStringMatcher.search1("binaragab", "binaragaa"));
    }
}