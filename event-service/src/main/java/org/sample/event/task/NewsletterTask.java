package org.sample.event.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NewsletterTask {

    private static final Logger log = LoggerFactory.getLogger(NewsletterTask.class);

    // Shoot the event every 30 seconds
    // FIXME: use a queue
    @Scheduled(fixedRate = 30000)
    public void newsletterEvent() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://localhost:9090/email", String.class);
        log.info(result);
    }

}
