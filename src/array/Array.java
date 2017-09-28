package array;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Array {
    static Scanner sc = new Scanner(System.in);
    static int cores = Runtime.getRuntime().availableProcessors();
    static ExecutorService service = Executors.newFixedThreadPool(cores);
    static int size = 20000000;
    static double[] array;

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {
        fillArray();
        System.out.print("Number of iterations: ");
        int iterations = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < iterations; i++) {
            System.out.println("ITERATION #" + (i+1));
            countViaThread();
            countViaThreadPool();
        }
        service.shutdown();
    }

    public static void countViaThreadPool() throws InterruptedException, ExecutionException, TimeoutException {
        double result = 0;
        long startTime = System.currentTimeMillis();
        List<FutureTask<Double>> taskList = getTaskList();

        for (int i = 0; i < taskList.size(); i++)
            service.submit(taskList.get(i));

        for (int i = 0; i < taskList.size(); i++)
            result += taskList.get(i).get();

        System.out.printf("Result [%s] was counted in %d ms using ThreadPool.\n\n",
                result,
                (System.currentTimeMillis() - startTime));
    }

    public static void countViaThread() throws InterruptedException, ExecutionException, TimeoutException {
        double result = 0;
        long startTime = System.currentTimeMillis();
        List<FutureTask<Double>> taskList = getTaskList();

        for (int i = 0; i < taskList.size(); i++){
            new Thread(taskList.get(i)).start();
        }

        for (int i = 0; i < taskList.size(); i++)
            result += taskList.get(i).get();

        System.out.printf("Result [%s] was counted in %d ms using Threads.\n",
                result,
                (System.currentTimeMillis() - startTime));
    }

    private static List<FutureTask<Double>> getTaskList() throws ExecutionException, InterruptedException {
        List<FutureTask<Double>> taskList = new ArrayList<>();

        int range = size/cores;
        for (int i = 0; i < cores; i++){
            int from = i*range;
            int to = (i == cores - 1) ? (size) : (from + range);
            FutureTask<Double> futureTask = new FutureTask<>(() -> {
                double res = 0;
                for (int j = from; j < to; j++) {
                    res += Math.sin(array[j]) + Math.cos(array[j]);
                }
                return res;
            });
            taskList.add(futureTask);
        }

        return taskList;
    }


    public static void fillArray() throws InterruptedException, ExecutionException, TimeoutException {
        double[] tempArray = new double[size];
        FutureTask<double[]> futureTask = new FutureTask<>(()->{
            for (int i = 0; i < size; i++)
                tempArray[i] = i + 1;
            return tempArray;
        });
        long start = System.currentTimeMillis();
        new Thread(futureTask).start();
        array = futureTask.get();
        System.out.printf("Array was filled in %d ms.\n\n", (System.currentTimeMillis() - start));
    }
}