package ca.sheridancollege.beans;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4918592134423844660L;
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	// Discount of 3 Types. based on what's passed
	public double discountCalc(int occupation, double moviePrice) {

		if (occupation == 1)
			moviePrice = 10;
		else if (occupation == 2)
			moviePrice = 8;
		else if (occupation == 3)
			moviePrice = 5;
		else if (occupation == 4)
			moviePrice = 5; // on a Tuesday.

		// for users
		if (auth.getName().equals("anonymousUser")) {
			System.out.println("I am not a user");
		} else {
			moviePrice = moviePrice - (moviePrice * 0.2);
		}

		return moviePrice;
	}

}
