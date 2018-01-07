package org.sample.subscription.mongodb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.subscription.SubscriptionServiceApplication;
import org.sample.subscription.domain.Subscription;
import org.sample.subscription.domain.enums.Gender;
import org.sample.subscription.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(SubscriptionServiceApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class SubscriptionRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionRepositoryTest.class);

    @Autowired
    private SubscriptionRepository repository;

    /**
     * Class level @DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
     * annotation can be used instead of this method to reset the context for
     * each test but execution will be much slower.
     */
    @Before
    public void cleanDB() {
        repository.deleteAll().block();
    }

    @Test
    public void shouldCreateASubscription() {

        // Given
        final Subscription subscription = new Subscription(
                "fabrejm@gmail.com",
                "JM Fabre",
                Gender.MALE,
                LocalDate.now(),
                true
        );

        // When
        final Subscription saved = repository.save(subscription).block();

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("fabrejm@gmail.com");
        assertThat(saved.getFirstName()).isEqualTo("JM Fabre");
        assertThat(saved.getConsent()).isTrue();
    }

    @Test
    public void shouldFindASubscriptionWhithItsEmail() {

        // Given
        final Subscription subscription = new Subscription(
                "fabrejm@gmail.com",
                "JM Fabre",
                Gender.MALE,
                LocalDate.now(),
                true
        );

        // When
        final Subscription saved = repository.save(subscription).block();

        // Then
        assertThat(repository.existsByEmail("fabrejm@gmail.com").defaultIfEmpty(Boolean.FALSE).block()).isTrue();
    }

    @Test
    public void shouldDeleteASubscription() {

        // Given
        final Subscription subscription = new Subscription(
                "fabrejm@gmail.com",
                "JM Fabre",
                Gender.MALE,
                LocalDate.now(),
                true
        );

        final Subscription saved = repository.save(subscription).block();

        // When
        repository.delete(saved).block();
        boolean exists = repository.existsByEmail(saved.getEmail()).defaultIfEmpty(Boolean.FALSE).block();

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    public void shouldReturnAllSubscriptions() {

        // Given
        repository.save(
                new Subscription("fabrejm@gmail.com",
                        "JM Fabre",
                        Gender.MALE,
                        LocalDate.now(),
                        true)
        ).block();

        repository.save(
                new Subscription("ana1980@gmail.com",
                        "Ana María Sánchez",
                        Gender.FEMALE,
                        LocalDate.now(),
                        true)
        ).block();

        // When
        final Iterable<Subscription> customers = repository.findAll().toIterable();

        // Then
        assertThat(customers).isNotNull();
        assertThat(customers.iterator()).hasSize(2);
    }

    @Test
    public void shouldReturnNoCustomers() {

        // Given

        // When
        final Iterable<Subscription> customers = repository.findAll().toIterable();

        // Then
        assertThat(customers).isNotNull();
        assertThat(customers.iterator()).hasSize(0);
    }
}
