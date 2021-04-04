package com.waracle.cakemgr.listener;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.service.CakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class ApplicationStartUpListenerTest {

    private ApplicationStartUpListener listener;
    private RestTemplate restTemplate = mock(RestTemplate.class);
    private CakeService cakeService = mock(CakeService.class);

    @BeforeEach
    public void setUp() {
        listener = new ApplicationStartUpListener(restTemplate, cakeService);
    }

    @Test
    public void shouldSaveCakesToDatabaseWhenOkResponseReceivedFromWebService() {
        ResponseEntity<Cake[]> responseEntity = new ResponseEntity<>(new Cake[]{Cake.builder().build()}, OK);
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(Cake[].class)))
                .thenReturn(responseEntity);

        listener.initDatabase();

        verify(cakeService).saveCakes(asList(responseEntity.getBody()));
    }

    @Test
    public void shouldNotSaveCakesToDatabaseWhenResponseBodyIsNull() {
        ResponseEntity<Cake[]> responseEntity = new ResponseEntity<>(null, OK);
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(Cake[].class)))
                .thenReturn(responseEntity);

        listener.initDatabase();

        verifyZeroInteractions(cakeService);
    }

    @Test
    public void shouldNotSaveCakesToDatabaseWhenNotOkResponseReceivedFromWebService() {
        ResponseEntity<Cake[]> responseEntity = new ResponseEntity<>(null, BAD_REQUEST);
        when(restTemplate.exchange(anyString(), eq(GET), eq(null), eq(Cake[].class)))
                .thenReturn(responseEntity);

        listener.initDatabase();

        verifyZeroInteractions(cakeService);
    }
}