import com.demo.Application;
import com.demo.dto.CreateUserRequest;
import com.demo.dto.UserLoginRequest;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();

    String userToBeCreated = "integrationtest";
    String userPassword = "Welcome@123";
    String userEmail = "abc@abc.com";

    @Test
    @Order(1)
    public void testCreateNewUser() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        CreateUserRequest createUserRequest = CreateUserRequest.builder().userName(userToBeCreated).password(userPassword).firstName("firstName").lastName("lastName").email(userEmail).build();
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                                            createURLWithPort("/users"),
                                            HttpMethod.POST, entity, String.class);
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        }

    @Test
    @Order(2)
    public void testAuthenticateCreatedUser() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        restTemplate.getRestTemplate().setRequestFactory(simpleClientHttpRequestFactory);
        UserLoginRequest userLoginRequest = UserLoginRequest.builder().userName(userToBeCreated).password(userPassword).build();
        HttpEntity<UserLoginRequest> entity = new HttpEntity<>(userLoginRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                createURLWithPort("/authenticate"), entity, String.class);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}