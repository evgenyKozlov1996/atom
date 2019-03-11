package ru.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.LoggerFactory;

public class Main {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);

    public static StringBuilder typedWordChars;
    public static StringBuilder wordChars;

    public static void main(String[] args) throws IOException {
        log.info("Peogramm started");
        System.out.println("Bulls and cows");

        WordProvider wordProvider = new WordProvider();
        String word = wordProvider.getWord();
        System.out.println("Please type any word");
        System.out.println(word);
        BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));
        String typedWord = br.readLine();
        typedWordChars = new StringBuilder(typedWord);
        wordChars = new StringBuilder(word);
        int tryCount = 0;
        while (!typedWord.equals(word) && tryCount<10) {
            int bullsCount = calculateBulls();
            int cowsCount = calculateCows();

            System.out.println("Cows: " + cowsCount + ". Bulls: " + bullsCount);
            System.out.println("Please type another word");
            typedWord = br.readLine();
            typedWordChars = new StringBuilder(typedWord);
            wordChars = new StringBuilder(word);
            tryCount++;
        }
        if(tryCount == 10) {
            System.out.println("You lose!");
        }
        else {
            System.out.println("Congratulations! You win!");
        }
    }

    public static int calculateBulls() {
        int bullsCount = 0;

        for (int index = 0; index < typedWordChars.length(); index++) {
            if (index < wordChars.length() && typedWordChars.charAt(index) == wordChars.charAt(index)) {
                bullsCount++;
                typedWordChars.deleteCharAt(index);
                wordChars.deleteCharAt(index);
                index--;
            }
        }

        return bullsCount;
    }

    public static int calculateCows() {
        int cowsCount = 0;
        for (int index = 0; index < typedWordChars.length(); index++) {
            for (int wordCharIndex = 0; wordCharIndex < wordChars.length(); wordCharIndex++) {
                if (typedWordChars.charAt(index) == wordChars.charAt(wordCharIndex)) {
                    cowsCount++;
                    typedWordChars.deleteCharAt(index);
                    wordChars.deleteCharAt(wordCharIndex);
                    index--;
                    break;
                }
            }
        }

        return cowsCount;
    }
}
