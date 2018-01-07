package org.sample.email.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sample.email.domain.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/email", produces = {TEXT_PLAIN_VALUE})
public class EmailController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    // FIXME: this is a mock,
    @RequestMapping(method = GET)
    public ResponseEntity<String> sendEmail() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            JsonNode subscriptions = restTemplate.getForObject("http://localhost:8080/subscriptions", JsonNode.class);
            if (subscriptions != null && subscriptions.size() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                List<Subscription> subscriptionList = null;

                subscriptionList = mapper.readValue(
                        mapper.treeAsTokens(subscriptions),
                        new TypeReference<List<Subscription>>() {
                        }
                );

                log.debug("Emails for newsletter:");
                subscriptionList.forEach(subscription -> log.info(subscription.getEmail()));
            }
        } catch (IOException e) {
            log.error("Error in databinding", e);
        }

        return new ResponseEntity<>("Newsletter sent", new HttpHeaders(), OK);
    }

}
