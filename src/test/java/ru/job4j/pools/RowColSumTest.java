package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {
    @Test
    public void testSerial() {
        int counter = 1;
        int size = 3;
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = counter++;
            }
        }
        long startTime = System.currentTimeMillis();
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        assertThat(sums[0].colSum()).isEqualTo(12);
        assertThat(sums[0].rowSum()).isEqualTo(6);
        assertThat(sums[1].colSum()).isEqualTo(15);
        assertThat(sums[1].rowSum()).isEqualTo(15);
        assertThat(sums[2].colSum()).isEqualTo(18);
        assertThat(sums[2].rowSum()).isEqualTo(24);
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        int counter = 1;
        int size = 3;
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = counter++;
            }
        }
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        assertThat(sums[0].colSum()).isEqualTo(12);
        assertThat(sums[0].rowSum()).isEqualTo(6);
        assertThat(sums[1].colSum()).isEqualTo(15);
        assertThat(sums[1].rowSum()).isEqualTo(15);
        assertThat(sums[2].colSum()).isEqualTo(18);
        assertThat(sums[2].rowSum()).isEqualTo(24);
    }
}