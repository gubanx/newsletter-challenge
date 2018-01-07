package org.sample.subscription.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.sample.subscription.domain.enums.Gender;
import org.sample.subscription.exception.SubscriptionServiceException;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * {@code Subscription}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public final class Subscription {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	@Indexed(unique = true)
	private String email;

	private String firstName;

	@JsonFormat(shape = Shape.STRING)
	private Gender gender;

	@JsonFormat(shape = Shape.STRING)
	private LocalDate dateOfBirth;

	@Transient
	private Boolean consent;

	private Subscription() {
	}

	public Subscription(String email, String firstName, Gender gender, LocalDate dateOfBirth, Boolean consent) {

		if (email == null) {
			throw new SubscriptionServiceException(BAD_REQUEST, "Subscription email can not be null.");
		}

		if (dateOfBirth == null) {
			throw new SubscriptionServiceException(BAD_REQUEST, "Date of birth can not be null.");
		}

		if (consent == null) {
			throw new SubscriptionServiceException(BAD_REQUEST, "Subscription consent can not be null.");
		}

		if (!consent) {
			throw new SubscriptionServiceException(BAD_REQUEST, "Subscription consent must be true.");
		}

        this.email = email;
		this.firstName = firstName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

	public ObjectId getId() {
		return id;
	}

    public String getEmail() {
        return email;
    }

	public String getFirstName() {
		return firstName;
	}

	public Gender getGender() {
		return gender;
	}

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Boolean getConsent() {
        return consent;
    }

}
