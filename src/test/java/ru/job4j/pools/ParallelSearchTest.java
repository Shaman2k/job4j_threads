package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelSearchTest {
    @Test
    public void whenIntArray() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 5;
        int expected = 4;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenStringArray() {
        String[] array = {"one", "two", "three", "four", "five"};
        String target = "three";
        int expected = 2;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenArrayIsSmall() {
        Integer[] array = {4, 3, 3, 4};
        int target = 3;
        int expected = 1;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenArrayIsLarge() {
        Integer[] array = new Integer[200];
        for (int i = 0; i < 200; i++) {
            array[i] = i + 1;
        }
        int target = 150;
        int expected = 149;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5};
        int target = 6;
        int expected = -1;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenArrayIsEmpty() {
        Integer[] array = {};
        int target = 0;
        int expected = -1;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenArrayIsNull() {
        Integer[] array = null;
        int target = 0;
        int expected = -1;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }

    @Test
    public void whenTargetIsNull() {
        Integer[] array = {1, 2, 3, 4, 5};
        Integer target = null;
        int expected = -1;
        int result = ParallelSearch.search(array, target);
        assertEquals(expected, result);
    }
}