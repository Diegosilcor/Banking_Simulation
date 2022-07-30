package com.devsus.challenge.controller;

import com.devsus.challenge.ChallengeApplication;
import com.devsus.challenge.dto.response.ClienteResponse;
import com.devsus.challenge.entity.ClienteEntity;
import com.devsus.challenge.mapper.ClienteMapper;
import com.devsus.challenge.repository.ClienteRepository;
import com.devsus.challenge.service.ClienteService;
import com.devsus.challenge.service.impl.ClienteServiceImpl;
import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerIntegrationTest{


    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void when_requestingACliente_thenReturningTheCliente() throws JSONException {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/clientes/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"clienteId\":1," +
                            "\"name\":\"José Lema\"," +
                            "\"gender\":null," +
                            "\"age\":null," +
                            "\"address\":\"Otavalo sn y principal\"," +
                            "\"phone\":\"098254785\"," +
                            "\"status\":true," +
                            "\"dni\":null}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
