package com.casestudy.demo.domain.insurance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	InsurancePolicyInvariantsTest.class,
	InsurancePolicyCalculationsTest.class
})
public class InsurancePolicyTestSuite {
}
