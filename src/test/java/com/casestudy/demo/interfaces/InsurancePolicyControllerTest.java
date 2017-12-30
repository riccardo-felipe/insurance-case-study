package com.casestudy.demo.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.casestudy.demo.interfaces.policies.InsurancePolicyDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.casestudy.demo.infrastructure.security.TokenAuthenticationService.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InsurancePolicyControllerTest {
	private static final String CUSTOMER_RICCARDO = "riccardo@example.com";
	private static final String CUSTOMER_EMILIA = "emilia@example.com";
	private static final String CUSTOMER_FELIPE = "felipe@example.com";

	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper mapper;

	@Before
	public void setUp() {
		this.mapper = new ObjectMapper();
	}

	@Test
	public void shouldGetAllInsurancePoliciesToCustomerRiccardo() throws Exception {
		shouldGetAllInsurancePoliciesToCustomer(CUSTOMER_RICCARDO, 3);
	}

	@Test
	public void shouldGetAllInsurancePoliciesToCustomerEmilia() throws Exception {
		shouldGetAllInsurancePoliciesToCustomer(CUSTOMER_EMILIA, 2);
	}

	@Test
	public void shouldGetAllInsurancePoliciesToCustomerFelipe() throws Exception {
		shouldGetAllInsurancePoliciesToCustomer(CUSTOMER_FELIPE, 0);
	}

	@Test
	public void shouldFindOneToCustomer() throws Exception {
		final MvcResult result = mockMvc
			.perform(get("/insurance-policy/1").headers(generateHttpHeaders(CUSTOMER_RICCARDO)))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		final String content = result.getResponse().getContentAsString();
		final InsurancePolicyDTO policy = mapper.readValue(content, InsurancePolicyDTO.class);

		assertNotNull(policy);
		assertEquals(policy.getId(), (Long) 1L);
		assertEquals(policy.getCustomer().getEmail(), CUSTOMER_RICCARDO);
	}

	@Test
	public void shouldNotFindOneToLoggedCustomerWhenInsuranceBelongsToAnotherCustomer() throws Exception {
		mockMvc
			.perform(get("/insurance-policy/1").headers(generateHttpHeaders(CUSTOMER_EMILIA)))
			.andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	public void shouldCreateAnInsurancePolicyToLoggedCustomer() throws Exception {
		final MvcResult result = mockMvc
			.perform(
				post("/insurance-policy")
					.headers(generateHttpHeaders(CUSTOMER_RICCARDO))
					.content("{\n" +
						"    \"customer\": {\n" +
						"        \"email\": \"riccardo@example.com\"\n" +
						"    },\n" +
						"    \"module\": {\n" +
						"        \"id\": 4,\n" +
						"        \"name\": \"Sports Equipment\"\n" +
						"    },\n" +
						"    \"selectedCoverage\": 5900\n" +
						"}")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andReturn();

		final String content = result.getResponse().getContentAsString();
		final InsurancePolicyDTO policy = mapper.readValue(content, InsurancePolicyDTO.class);

		assertNotNull(policy);
		assertEquals(policy.getId(), (Long) 6L);
		assertEquals(policy.getCustomer().getEmail(), CUSTOMER_RICCARDO);
		assertEquals(policy.getModule().getId(), (Long) 4L);
		assertTrue(isEqual(policy.getSelectedCoverage(), parseValue("5900")));

		shouldDeleteForLoggedUser(policy.getId());
	}

	public void shouldDeleteForLoggedUser(Long id) throws Exception {
		mockMvc
			.perform(
				delete("/insurance-policy/" + id)
					.headers(generateHttpHeaders(CUSTOMER_RICCARDO))
			)
			.andExpect(status().isNoContent());
	}

	@Test
	public void shouldNotDeleteWhenIllegalArgument() throws Exception {
		final String errorMessage = "insurance id 100 for customer riccardo@example.com not found";
		mockMvc
			.perform(
				delete("/insurance-policy/100")
					.headers(generateHttpHeaders(CUSTOMER_RICCARDO))
			)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.error", (errorMessage)).value(is(errorMessage)));
	}

	public void shouldGetAllInsurancePoliciesToCustomer(String username, int expectedTotal) throws Exception {
		final MvcResult result = mockMvc
			.perform(get("/insurance-policy").headers(generateHttpHeaders(username)))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		final String content = result.getResponse().getContentAsString();
		final TypeReference<List<InsurancePolicyDTO>> typeReference = new TypeReference<List<InsurancePolicyDTO>>() {};
		final List<InsurancePolicyDTO> policies = mapper.readValue(content, typeReference);
		assertEquals(policies.size(), expectedTotal);
	}

	private boolean isEqual(BigDecimal one, BigDecimal other) {
		return one.compareTo(other) == 0;
	}

	private BigDecimal parseValue(String value) {
		return new BigDecimal(value).setScale(2, BigDecimal.ROUND_UP);
	}

	private HttpHeaders generateHttpHeaders(String username) {
		final HttpHeaders httpHeaders = new HttpHeaders();
						  httpHeaders.add(HEADER_STRING, TOKEN_PREFIX + " " + generateToken(username));
		return httpHeaders;
	}

	private String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, SECRET)
			.compact();
	}
}
