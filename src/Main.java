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
        int[] ns = {100, 1000, 10000, 100000, 300000, 500000, 1000000, 3000000, 5000000, 10000000};
        List<Long[]> times = new ArrayList<>();

        for (int n :
                ns) {
//            if (n > ns[2]) continue;
            System.out.println("\n\n ==================== [username-total : " + n + " ]");

            int[] indexes = new java.util.Random().ints(0, n).distinct().limit((int) (10)).toArray();

            Integer[] intIndexs = Arrays.stream(indexes).boxed().toArray(Integer[]::new);

            lTc = new LinkedList<>(Arrays.asList(intIndexs));

            filePath = String.format("dist/usernames-%d.txt", n);

            times.add(new Long[]{(long) 9999999, (long) n});

            System.out.println("\n=================================================== [BF]\n");
            times.add(Main.testBF());
            System.out.println("\n=================================================== [KMP]\n");
            times.add(Main.testKMP());
            System.out.println("\n=================================================== [SMASCII]\n");
            times.add(Main.testSMASCII());
        }

        System.out.println("\n\n\n\n");
        for (Long[] time :
                times) {
            System.out.printf("%d\t%d\n", time[0], time[1]);
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

    public static Long[] testKMP() {
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

        li.clear();
        li.addAll(getItem());

        String text = contentBuilder.toString();

        long sum = 0;
        for (String tc :
                li) {
            times.add(wrapperTimer(() -> {
                kmp.set(new KmpStringMatcher(tc));
            }));
            sum += wrapperTimerAndPrint(tc, () -> {
                kmp.get().search(text);
            });
        }
        long timeSum = 0;
        for (long time :
                times) {
            timeSum += time;
        }

        printTime(namePreProc, timeSum / times.size());
        return new Long[]{timeSum / times.size(), sum / li.size()};
    }

    public static Long[] testBF() {
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

        li.clear();
        li.addAll(getItem());


        String text = contentBuilder.toString();

        long sum = 0;
        for (String tc :
                li) {
            sum += wrapperTimerAndPrint(tc, () -> NaiveStringMatcher.search1(tc, text));
        }
        return new Long[]{(long) 0, sum / li.size()};
    }

    public static Long[] testSMASCII() {
        ArrayList<String> li = new ArrayList<>();

        long preProc = wrapperTimerAndPrint(namePreProc, () -> {
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

        li.clear();
        li.addAll(getItem());

        long sum = 0;

        for (String tc :
                li) {
            sum += wrapperTimerAndPrint(tc, () -> {
                item.find(tc);
            });
        }

        return new Long[]{preProc, sum / li.size()};
    }


    public static ArrayList<String> getItem() {
        String[] tc = {
                "affan.dhia.a",
                "affan.ardhiva",
                "ardhiva.affan",
                "affan.dhia.a201",
                "affan.dhia.a211",
                "affan.dhia.a231",
                "affan.dhia.a251",
                "ayu.indah.d231",
                "ayu.dhia.a251",
                "ardhiva.indah.a251",
        };
        return new ArrayList<>(Arrays.asList(tc));
    }
}
