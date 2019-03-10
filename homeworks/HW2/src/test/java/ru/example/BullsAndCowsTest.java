package ru.example;

import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class BullsAndCowsTest {
    @Test
    public void TestBullsAndCows1(){
        Main.wordChars=new StringBuilder("aasty");
        Main.typedWordChars = new StringBuilder("aatsy");

        assertThat(Main.calculateBulls(), is(equalTo(3)));
        assertThat(Main.calculateCows(), is(equalTo(2)));
    }

    @Test
    public void TestBullsAndCows2(){
        Main.wordChars=new StringBuilder("aasty");
        Main.typedWordChars = new StringBuilder("puio");

        assertThat(Main.calculateBulls(), is(equalTo(0)));
        assertThat(Main.calculateCows(), is(equalTo(0)));
    }

    @Test
    public void TestBullsAndCows3(){
        Main.wordChars=new StringBuilder("aasty");
        Main.typedWordChars = new StringBuilder("aasty");

        assertThat(Main.calculateBulls(), is(equalTo(5)));
        assertThat(Main.calculateCows(), is(equalTo(0)));
    }
}
