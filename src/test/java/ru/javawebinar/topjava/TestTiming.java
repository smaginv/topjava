package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestTiming {
    private static final Logger log = LoggerFactory.getLogger("");
    private static final StringBuilder result = new StringBuilder();
    private static long totalTime = 0;

    public static final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            totalTime += TimeUnit.NANOSECONDS.toMillis(nanos);
            String result = String.format("%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            TestTiming.result.append(result).append('\n');
            log.info(result + " ms");
        }
    };

    public static final ExternalResource summary = new ExternalResource() {
        @Override
        protected void before() {
            result.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n" + "\nTest                 Duration, ms" +
                    "\n" + "\n" + result +
                    "\n" + "Total time: " + totalTime + " ms\n");
        }
    };
}
