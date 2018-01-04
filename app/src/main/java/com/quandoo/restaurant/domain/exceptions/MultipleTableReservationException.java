package com.quandoo.restaurant.domain.exceptions;

/**
 * Created by Behzad on 1/3/2018.
 */

public class MultipleTableReservationException extends RuntimeException {

    @Override
    public String getMessage() {
        return "The customer can not have multiple reservations.";
    }
}
