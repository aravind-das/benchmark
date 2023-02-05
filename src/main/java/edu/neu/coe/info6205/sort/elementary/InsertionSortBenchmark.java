package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.TimeLogger;

import java.util.Arrays;
import java.util.Collections;

public class InsertionSortBenchmark {
    public static Integer[] generateRandomArray(int len, int min, int max) {
        Integer[] arr = new Integer[len];

        for (int i = 0; i < len; i++) {
            arr[i] = ((int) (Math.random() * (max - min + 1) + min) );
        }

        return arr;
    }

    class BenchmarkUtil {
        private Integer[] arr;
        private Benchmark_Timer<Integer[]> b;
        private final TimeLogger[] timeLoggers = {
                new TimeLogger("Raw time per run (mSec): ", (time, n) -> time)
        };

        BenchmarkUtil(Integer[] arr, String description) {
            this.arr = arr;
            this.b = new Benchmark_Timer<>(
                    description,
                    null,
                    (x) -> new InsertionSort<Integer>().sort(arr.clone(), 0, arr.length),
                    null
            );
        }

        public double runBenchmark(int runs) {
            return b.run(arr, runs);
        }

        public void logTime(double time) {
            for (TimeLogger timeLogger : timeLoggers) {
                timeLogger.log(time, arr.length);
            }
        }
    }

    public static void main(String[] args) {
        int RUNS = 100;
        int START_N = 1000;
        int END_N = 16000;
        int VAL_START = -100000;
        int VAL_END = 100000;

        for (int n = START_N; n <= END_N; n *= 2) {
            Integer[] testArray = generateRandomArray(n, VAL_START, VAL_END);

            BenchmarkUtil b = new InsertionSortBenchmark()
                    .new BenchmarkUtil(testArray, "Random Array of size " + n);

            double time1 = b.runBenchmark(RUNS);

            b.logTime(time1);

            Arrays.sort(testArray,0 , n/2);

            b = new InsertionSortBenchmark()
                    .new BenchmarkUtil(testArray, "Partially Sorted Array of size " + n);

            double time2 = b.runBenchmark(RUNS);

            b.logTime(time2);

            Arrays.sort(testArray);

            b = new InsertionSortBenchmark()
                    .new BenchmarkUtil(testArray, "Sorted Array of size " + n);

            double time3 = b.runBenchmark(RUNS);

            b.logTime(time3);

            Arrays.sort(testArray, Collections.reverseOrder());

            b = new InsertionSortBenchmark()
                    .new BenchmarkUtil(testArray, "Reverse Sorted Array of size " + n);

            double time4 = b.runBenchmark(RUNS);

            b.logTime(time4);

            System.out.println();
        }
    }
}
