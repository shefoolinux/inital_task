package com.example.siemens_initial_project.siemens_initial_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SiemensInitialProjectApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void contextLoads() {
	}

}
