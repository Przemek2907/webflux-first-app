package com.app.exercises;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class RandomTests {

    @Test
    public void hashMapFromStringsStreams() {
        String myString = "Ala ma kota, kot ma Ale";

        ConcurrentMap<Character, Long> groupedLetters = myString.chars()
                .parallel()
                .mapToObj(character -> ((char) character))
                .map(this::transform)
                .collect(Collectors.groupingByConcurrent(singleChar -> singleChar, Collectors.counting()));

        System.out.println(groupedLetters);
    }

    private char transform (char character) {
        try {
            System.out.println(Thread.currentThread().getName());
            int cores = Runtime.getRuntime().availableProcessors();
            System.out.println(cores);
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return character;
    }

    @Test
    public void concurrentHashMapFromStrings() {
        String myString = "Ala ma kota, kot ma Ale";
        //TODO zamiast executors service trzeba dać fork-join / recursive task
        ExecutorService es = Executors.newCachedThreadPool();
        ConcurrentMap<Character, Integer> charactersGrouped = new ConcurrentHashMap<>();

        final char[] chars = myString.toCharArray();

        for (char charItem : chars) {
            es.submit(() -> {
                if (charactersGrouped.containsKey(charItem)) {
                    transform(charItem);
                    charactersGrouped.put(charItem, charactersGrouped.get(charItem) + 1);
                } else {
                    charactersGrouped.put(charItem, 1);
                }
            });
        }

        System.out.println(charactersGrouped);

    }


    @Test
    public void reverseArray() {
        final int[] myArray = {3,6,8,12,45,9,90};
        int halfSizeOfArray = myArray.length % 2 == 0 ? myArray.length/2 : (myArray.length + 1)/2;
        System.out.println(halfSizeOfArray);

        for (int i = 0; i < halfSizeOfArray; i++) {
            int temp = myArray[i];
            myArray[i] = myArray[myArray.length - 1 - i];
            myArray[myArray.length - 1 - i] = temp;
        }

        System.out.println(Arrays.toString(myArray));
    }
}
