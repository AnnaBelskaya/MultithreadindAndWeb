package calculator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Counting {
    private Counting(){}

    public static double add(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Double> callable = () -> a+b;
        FutureTask<Double> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static double sub(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Double> callable = () -> a-b;
        FutureTask<Double> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static double mul(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Double> callable = () -> a*b;
        FutureTask<Double> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static double div(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Double> callable = () -> a/b;
        FutureTask<Double> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static int mod(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = () -> (int)a%(int)b;
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static boolean more(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Boolean> callable = () -> a>b;
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static boolean less(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Boolean> callable = () -> a<b;
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }

    public static boolean equal(double a, double b) throws ExecutionException, InterruptedException {
        Callable<Boolean> callable = () -> a==b;
        FutureTask<Boolean> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask.get();
    }
}
