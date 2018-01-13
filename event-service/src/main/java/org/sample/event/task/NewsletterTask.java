package org.sample.event.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NewsletterTask {

    private static final Logger log = LoggerFactory.getLogger(NewsletterTask.class);

    @Value("${email-service.url}")
    private String emailUrl;

    // Shoot the event every 30 seconds
    // FIXME: use a queue
    @Scheduled(fixedRate = 30000)
    public void newsletterEvent() {
        log.info("emailUrl: " + emailUrl);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(emailUrl, String.class);
        log.info(result);
    }

}
