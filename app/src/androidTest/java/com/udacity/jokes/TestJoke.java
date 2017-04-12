package com.udacity.jokes;


import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestJoke extends ApplicationTestCase<Application> {

    private String joke;

    public TestJoke(Class<Application> applicationClass) {
        super(applicationClass);
    }

    public void testJoke() {
        try {
            final CountDownLatch cdl = new CountDownLatch(1);
            new GCETask().execute(new onReceiveJoke() {
                @Override
                public void onReceive(boolean success, String joke) {
                    TestJoke.this.joke = joke;
                    assertEquals(success, true);
                    cdl.countDown();

                }

            });
            cdl.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
        } catch (Exception ignored) {
            fail(ignored.getMessage());
        }
    }
}
