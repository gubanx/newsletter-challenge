package org.sample.subscription.repository;

import org.bson.types.ObjectId;
import org.sample.subscription.domain.Subscription;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface SubscriptionRepository extends ReactiveCrudRepository<Subscription, ObjectId> {

    Mono<Boolean> existsByEmail(String email);

}
