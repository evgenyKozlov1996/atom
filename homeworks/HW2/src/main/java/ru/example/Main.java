package ru.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Bulls and cows");

        WordProvider wordProvider = new WordProvider();
        String word = wordProvider.getWord();
        System.out.println("Please type any word");
        System.out.println(word);
        BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));
        String typedWord = br.readLine();
        while(!typedWord.equals(word)){
            int cowsCount = 0;
            int bullsCount = 0;

            List<Character> typedChars = typedWord.toCharArray();
            char[] wordChars = word.toCharArray();
            for(int index = 0; index < typedChars.length; index ++){
                if(index < wordChars.length && typedChars[index] == wordChars[index]){
                    bullsCount ++;
                    typedChars.
                }
            }

            System.out.println("Cows: " + cowsCount + ". Bulls: " + bullsCount);
            System.out.println("Please type another word");
            typedWord = br.readLine();
        }
        System.out.println("Congratulations! You win!");
    }
}
