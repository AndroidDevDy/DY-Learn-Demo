package com.daiy.learn;

import com.daiy.learn.test_rxjava.RxJavaExample1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        try {
            new RxJavaExample1().test12();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(4, 2 + 2);
    }
}