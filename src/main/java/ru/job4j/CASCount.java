package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer ref;
        Integer newCount;
        do {
            ref = count.get();
            newCount = ref + 1;
        } while (!count.compareAndSet(ref, newCount));
    }

    public int get() {
        return count.get();
    }
}