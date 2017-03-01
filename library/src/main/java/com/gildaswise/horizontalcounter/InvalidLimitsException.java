package com.gildaswise.horizontalcounter;

/**
 * Created by Gildaswise on 01/03/2017.
 */

class InvalidLimitsException extends RuntimeException {

    public InvalidLimitsException() {
        super("You should use valid min and max values, please verify them!");
    }
}
