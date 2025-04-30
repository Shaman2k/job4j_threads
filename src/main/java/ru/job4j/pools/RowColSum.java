package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public record Sums(int rowSum, int colSum) {
    }

    private static Sums calculate(int[][] matrix, int index) {
        int size = matrix.length;
        int colSum = 0;
        int rowSum = 0;
        for (int i = 0; i < size; i++) {
            rowSum += matrix[index][i];
            colSum += matrix[i][index];
            }
        return new Sums(rowSum, colSum);
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = calculate(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        CompletableFuture<?>[] futures = new CompletableFuture[size];
        for (int i = 0; i < size; i++) {
            int index = i;
            futures[index] = CompletableFuture.runAsync(() ->
                    sums[index] = calculate(matrix, index));
        }
        CompletableFuture.allOf(futures).get();
        return sums;
    }
}
