package org.sample.subscription.controller;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.sample.subscription.domain.Subscription;
import org.sample.subscription.exception.SubscriptionServiceException;
import org.sample.subscription.repository.SubscriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.sample.subscription.domain.enums.Gender.FEMALE;
import static org.sample.subscription.domain.enums.Gender.MALE;
import static org.springframework.http.HttpStatus.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

	@Mock
	private SubscriptionRepository repository;

	@InjectMocks
	private SubscriptionController controller;

	@Test
	public void shouldReturnAllSubscriptions() {

		// Given
		final List<Subscription> subscriptions = asList(
				new Subscription("pepe@gmail.com", "Pepe", MALE, LocalDate.now(), true),
				new Subscription("amparo@gmail.com", "Amparo", FEMALE, LocalDate.now(), true));
		when(repository.findAll()).thenReturn(Flux.fromIterable(subscriptions));

		// When
		final ResponseEntity<List<Subscription>> response = controller.getAllSubscriptions().block();

		// Then
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat((Iterable<Subscription>) response.getBody()).asList().containsAll(subscriptions);
	}

	@Test
	public void shouldReturnEmptyBodyWhenNoSubscriptions() {

		// Given
		when(repository.findAll()).thenReturn(Flux.empty());

		// When
		final ResponseEntity<List<Subscription>> response = controller.getAllSubscriptions().block();

		// Then
		assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
	}

	@Test
	public void shouldReturnOneSubscriptionById() {

		// Given
		final Subscription subscription = new Subscription("pepe@gmail.com", "Pepe", MALE, LocalDate.now(), true);
		when(repository.findById(any(ObjectId.class))).thenReturn(Mono.just(subscription));

		// When
		final ResponseEntity<Subscription> response = controller.getSubscription(ObjectId.get()).block();

		// Then
		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody()).isEqualTo(subscription);
	}

	@Test
	public void shouldReturn404IfSubscriptionIsNotFound() {

		// Given
		when(repository.findById(any(ObjectId.class))).thenReturn(Mono.empty());

		// When
		final ResponseEntity<Subscription> response = controller.getSubscription(ObjectId.get()).block();

		// Then
		assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	public void shouldAddANewSubscription() {

		// Given
		final Subscription newSubscription = new Subscription("pepe@gmail.com", "Pepe", MALE, LocalDate.now(), true);

		final ObjectId id = ObjectId.get();
		ReflectionTestUtils.setField(newSubscription, "id", id);

		when(repository.existsByEmail(any(String.class))).thenReturn(Mono.just(false));
		when(repository.save(any(Subscription.class))).thenReturn(Mono.just(newSubscription));

		// When
		final ResponseEntity<?> response = controller.addSubscription(newSubscription).block();

		// Then
		assertThat(response.getStatusCode()).isEqualTo(CREATED);
		assertThat(response.getHeaders().getLocation().toString()).isEqualTo(format("/subscriptions/%s", id));
	}

	@Test
	public void shouldNotAddACustomerIfSubscriptionAlreadyExists() {

		// Given
		when(repository.existsByEmail(any(String.class))).thenReturn(Mono.just(true));
		final Subscription subscription = new Subscription("pepe@gmail.com", "Pepe", MALE, LocalDate.now(), true);

		// When

		// Then
		assertThatThrownBy(() -> controller.addSubscription(subscription).block())
			.isInstanceOf(SubscriptionServiceException.class)
			.hasMessageContaining("Subscription already exists");
	}

	@Test
	public void shouldDeleteAnExistingSubscription() {

		// Given
		when(repository.existsById(any(ObjectId.class))).thenReturn(Mono.just(true));
		when(repository.deleteById(any(ObjectId.class))).thenReturn(Mono.empty());
		final ObjectId id = ObjectId.get();

		// When
		final ResponseEntity<?> response = controller.deleteSubscription(id).block();

		// Then
		assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
	}

}
