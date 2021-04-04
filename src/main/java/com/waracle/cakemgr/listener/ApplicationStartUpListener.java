package com.waracle.cakemgr.listener;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.service.CakeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@Component
public class ApplicationStartUpListener {

    private final RestTemplate restTemplate;
    private final CakeService cakeService;

    @EventListener(ApplicationReadyEvent.class)
    public void initDatabase() {
        String url = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
        ResponseEntity<Cake[]> responseEntity = restTemplate.exchange(url, GET, null, Cake[].class);
        if (responseEntity.getStatusCode() == OK && responseEntity.getBody() != null) {
            Cake[] cakes = responseEntity.getBody();
            cakeService.saveCakes(asList(cakes));
        }
    }
}
