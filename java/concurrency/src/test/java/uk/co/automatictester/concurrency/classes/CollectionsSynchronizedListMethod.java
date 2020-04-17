package uk.co.automatictester.concurrency.classes;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class CollectionsSynchronizedListMethod {

    private final int threads = 8;
    private final int loopCount = 1_000;
    private List<Integer> list;

    private Runnable r = () -> {
        for (int i = 0; i < loopCount; i++) {
            int value = ThreadLocalRandom.current().nextInt();
            list.add(value);
        }
    };

    @Test(invocationCount = 5)
    public void testList() throws InterruptedException {
        list = new ArrayList<>();
        test(r);
        assertThat(list.size(), not(equalTo(8_000)));
    }

    @Test
    public void testSynchronizedList() throws InterruptedException {
        list = Collections.synchronizedList(new ArrayList<>());
        test(r);
        assertThat(list.size(), equalTo(8_000));
    }

    private void test(Runnable runnable) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(runnable);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        int value = ThreadLocalRandom.current().nextInt();
        if (list.contains(value)) {
            log.info("x");
        }
    }
}
