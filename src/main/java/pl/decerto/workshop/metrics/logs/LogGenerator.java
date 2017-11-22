package pl.decerto.workshop.metrics.logs;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogGenerator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void generate() {
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    logger.info("Przykladowy log nr {}", i++);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }}
        ).start();

    }


    @PostConstruct
    public void generateException() {
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    logger.error("Błąd", new RuntimeException(String.format("Przykladowy wyjątek nr %s", i++)));
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }}
        ).start();
    }
}
