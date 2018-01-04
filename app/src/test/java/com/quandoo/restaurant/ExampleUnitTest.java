package com.quandoo.restaurant.viewmodel;

import net.jodah.concurrentunit.Waiter;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Waiter waiter;
    @Test
    public void addition_isCorrect() throws Exception {
        waiter = new Waiter();
        Single<String> test = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                e.onSuccess("test");
            }
        });

        test.subscribe(result->{
            assertEquals(result,"test");
            waiter.resume();
        });

        waiter.await(1000);
    }
}