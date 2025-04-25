package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] source;
    private final T target;
    private final int from;
    private final int to;
    private static final int LIMIT = 10;

    public ParallelSearch(T[] source, T target, int from, int to) {
        this.source = source;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < LIMIT) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch(source, target, from, middle);
        leftSearch.fork();
        int left = leftSearch.join();
        ParallelSearch<T> rightSearch = new ParallelSearch(source, target, middle + 1, to);
        rightSearch.fork();
        int right = rightSearch.join();
        return left == -1 ? right : left;
    }

    private Integer linearSearch() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (target.equals(source[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    public static <T> int search(T[] source, T target) {
        if (source == null || source.length == 1 || target == null) {
            return -1;
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            return forkJoinPool.invoke(new ParallelSearch<T>(source, target, 0, source.length - 1));
        }
    }
}
