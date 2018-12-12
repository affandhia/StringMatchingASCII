import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {
    private static SMASCII item = new SMASCII();

    public static void main(String[] args) {
        System.out.println(NaiveStringMatcher.search1("nara", "binaragaa"));

        wrapperTimer(Main::testSMASCII);
        wrapperTimer(Main::testKMP);

    }

    public static void wrapperTimer(Runnable func) {
        wrapperTimer("", func);
    }

    public static void wrapperTimer(String title, Runnable func) {
//        Instant start = Instant.now();
//        func.run();
//        Instant finish = Instant.now();
//        long timeElapsed = Duration.between(start, finish).toNanos();

        long start = System.nanoTime();
        func.run();
        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        System.out.printf("%s%sTotal time: %d\n", title, (title.compareTo("") == 0 ? "" : " "), timeElapsed);
    }

    public static void testKMP() {
        KmpStringMatcher kmp = new KmpStringMatcher("binaraga");
        System.out.println(kmp.search("binaragaa"));
    }

    public static void testSMASCII() {
        ArrayList<String> li = new ArrayList<>();

        wrapperTimer("preprocessing time smascii |", () -> {
            try (Stream<String> stream = Files.lines(Paths.get("dist/usernames-3000000.txt"))) {
                stream.forEach(e -> {
                    if (Math.random() > 0.95 || li.size() == 0) {
                        li.add(e);
                    }
                    item.add(e);
                });
            } catch (Exception e) {
                System.out.println("file not found");
            }
        });

        for (String tc :
                li) {
            wrapperTimer("searcing time smascii |", () -> {
                System.out.println(String.format("%s found? %b", tc, item.find(tc)));
            });
        }

    }
}
