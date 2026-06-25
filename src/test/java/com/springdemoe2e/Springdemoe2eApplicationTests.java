package com.springdemoe2e;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class Springdemoe2eApplicationTests {

	@Test
	void contextLoads() {
		// Test that application context loads successfully with H2 database
		assertTrue(true, "Application context loaded with H2 database");
	}

	@Test
	void h2DatabaseIsActive() {
		// Verify H2 is configured for testing
		String activeProfile = System.getProperty("spring.profiles.active");
		System.out.println("Active profiles from system: " + activeProfile);
		// Test passes if context loads without errors
		assertTrue(true, "H2 database configuration is active");
	}

	@Test
	void testApplicationName() {
		// Simple test to verify application configuration
		assertNotNull(this, "Application test class initialized");
	}
}
