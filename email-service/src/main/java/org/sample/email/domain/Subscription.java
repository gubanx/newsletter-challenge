package org.sample.email.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * {@code Subscription}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public final class Subscription {

	private String id;
	private String email;
	private String firstName;
	private String gender;
	private String dateOfBirth;


	private Subscription() {
	}

	public String getId() {
		return id;
	}

    public String getEmail() {
        return email;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getGender() {
		return gender;
	}

    public String getDateOfBirth() {
        return dateOfBirth;
    }

}
