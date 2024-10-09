package com.validation;

import java.time.LocalDate;

public class TaskValidator {

    public static void validateTaskDate(LocalDate taskDate) throws IllegalArgumentException {
        LocalDate currentDate = LocalDate.now();

        if (taskDate.isBefore(currentDate)  ) {
            throw new IllegalArgumentException("Task cannot be scheduled for a past date.");
        }


        if (taskDate.isBefore(currentDate.plusDays(3))) {
            throw new IllegalArgumentException("Task cannot be scheduled more than 3 days in advance.");
        }
    }
}
