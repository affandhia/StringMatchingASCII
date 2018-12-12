import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Main {
    private static SMASCII item = new SMASCII();
    private static String filePath = "dist/usernames-100.txt";
    private static List<Integer> lTc;
    private static String namePreProc = "preprocessing-time";

    public static void main(String[] args) {
//        System.out.println(NaiveStringMatcher.search1("nara", "binaragaa"));
        int[] ns = {100, 1000, 10000, 100000, 300000, 500000, 1000000, 3000000, 5000000};

        for (int n :
                ns) {
//            if (n > ns[0]) continue;
            System.out.println("username-total" + n);

            int[] indexes = new java.util.Random().ints(0, n).distinct().limit((int) (10)).toArray();

            Integer[] intIndexs = Arrays.stream( indexes ).boxed().toArray( Integer[]::new );

            lTc = new LinkedList<>(Arrays.asList(intIndexs));

            filePath = String.format("dist/usernames-%d.txt", n);

            System.out.println("\n=================================================== [BF]\n");
            Main.testBF();
            System.out.println("\n=================================================== [KMP]\n");
            Main.testKMP();
            System.out.println("\n=================================================== [SMASCII]\n");
            Main.testSMASCII();
        }


    }

    public static long wrapperTimerAndPrint(Runnable func) {
        return wrapperTimerAndPrint("", func);
    }

    public static long wrapperTimerAndPrint(String title, Runnable func) {

        long timeElapsed = wrapperTimer(func);

        printTime(title, timeElapsed);

        return timeElapsed;
    }

    public static long wrapperTimer(Runnable func) {

//        Instant start = Instant.now();
//        func.run();
//        Instant finish = Instant.now();
//        long timeElapsed = Duration.between(start, finish).toNanos();
        long start = System.nanoTime();
        func.run();
        long finish = System.nanoTime();

        return finish - start;
    }

        public static void printTime(String title, long timeElapsed) {
        System.out.printf("%s\t%d\n", title, timeElapsed);

    }

    public static void testKMP() {
        AtomicReference<KmpStringMatcher> kmp = new AtomicReference<>();
        StringBuilder contentBuilder = new StringBuilder();
        ArrayList<String> li = new ArrayList<>();

        List<Long> times = new LinkedList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            AtomicInteger counter = new AtomicInteger();
            stream.forEach(s -> {

                if (lTc.indexOf(counter.getAndIncrement()) >= 0) {
                    li.add(s);
                }
                contentBuilder.append(s).append("\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text = contentBuilder.toString();

        for (String tc :
                li) {
            times.add(wrapperTimer(() -> {
                kmp.set(new KmpStringMatcher(tc));
            }));
            wrapperTimerAndPrint(tc, () -> {
                kmp.get().search(text);
            });
        }
        long sum = 0;
        for (long time :
                times) {
            sum += time;
        }

        printTime(namePreProc, sum / times.size());
    }

    public static void testBF() {
        printTime(namePreProc, 0);
        AtomicReference<KmpStringMatcher> kmp = new AtomicReference<>();
        StringBuilder contentBuilder = new StringBuilder();
        ArrayList<String> li = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            AtomicInteger counter = new AtomicInteger();
            stream.forEach(s -> {

                if (lTc.indexOf(counter.getAndIncrement()) >= 0) {
                    li.add(s);
                }
                contentBuilder.append(s).append("\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        String text = contentBuilder.toString();

        for (String tc :
                li) {
            wrapperTimerAndPrint(tc, () -> NaiveStringMatcher.search1(tc, text));
        }
    }

    public static void testSMASCII() {
        ArrayList<String> li = new ArrayList<>();

        wrapperTimerAndPrint(namePreProc, () -> {
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                AtomicInteger counter = new AtomicInteger();
                stream.forEach(e -> {
                    if (lTc.indexOf(counter.getAndIncrement()) >= 0) {
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
            wrapperTimerAndPrint(tc, () -> {
                item.find(tc);
            });
        }

    }
}
