package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestTiming {
    private static final Logger log = LoggerFactory.getLogger("");
    private static final StringBuilder results = new StringBuilder();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format(ANSI_YELLOW + "%-25s" + ANSI_GREEN + "%7d" + ANSI_RESET,
                    description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            log.info(result + ANSI_GREEN + " ms" + ANSI_RESET);
        }
    };

    public static final ExternalResource summary = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info(ANSI_YELLOW + "\n" + "\nTest" + ANSI_GREEN + "                Duration, ms" +
                    "\n" + "\n" + results);
        }
    };
}
