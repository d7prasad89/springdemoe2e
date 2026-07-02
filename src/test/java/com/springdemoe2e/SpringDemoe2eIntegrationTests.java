package com.springdemoe2e;

import com.springdemoe2e.model.Song;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("qa")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class SpringDemoe2eIntegrationTests {
    @Autowired
    private org.springframework.core.env.Environment env;

    @org.springframework.boot.test.web.server.LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
        String[] activeProfiles = env.getActiveProfiles();
        System.out.println("Active profiles from Environment: " + activeProfiles[0]);
    }

    @Test
//    @Commit -- This annotation is commented out to ensure that the test data is rolled back after the test, keeping the database clean for other tests.
    @Sql(scripts = "/sql/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllSongs_returnsOk() {
        org.springframework.boot.resttestclient.TestRestTemplate rest = new org.springframework.boot.resttestclient.TestRestTemplate();
        ResponseEntity<Song[]> resp = rest.getForEntity("http://localhost:" + port + "/api/songs", Song[].class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
        System.out.println("Response body: " + java.util.Arrays.toString(resp.getBody()));
        assertThat(resp.getBody()).isNotNull();
    }
}
