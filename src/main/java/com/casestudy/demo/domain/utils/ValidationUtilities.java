package com.casestudy.demo.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtilities {
	public static void isPositiveValue(BigDecimal value, String property) {
		final String message = String.format("%s amount should be positive", property);
		Assert.state(value.compareTo(BigDecimal.ZERO) > -1, message);
	}
	public static void isGreaterThanZero(BigDecimal value, String property) {
		final String message = String.format("%s amount should be positive and greater than zero", property);
		Assert.state(value.compareTo(BigDecimal.ZERO) > 0, message);
	}
}
