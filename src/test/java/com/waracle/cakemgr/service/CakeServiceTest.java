package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CakeServiceTest {

    private CakeService cakeService;

    private CakeRepository cakeRepository = mock(CakeRepository.class);

    @BeforeEach
    public void setUp() {
        cakeService = new CakeService(cakeRepository);
    }

    @Test
    public void shouldGetAllCakes() {
        List<Cake> cakes = singletonList(Cake.builder()
                .title("title")
                .description("desc")
                .image("img")
                .build());
        List<CakeEntity> cakeEntities = singletonList(CakeEntity.builder()
                .title("title")
                .description("desc")
                .image("img")
                .build());
        when(cakeRepository.findAll()).thenReturn(cakeEntities);

        List<Cake> result = cakeService.getAllCakes();

        assertEquals(cakes, result);
        verify(cakeRepository).findAll();
    }

    @Test
    public void shouldSaveCakes() {
        List<Cake> cakes = asList(
                Cake.builder()
                        .title("title1")
                        .description("description1")
                        .image("image1")
                        .build(),
                Cake.builder()
                        .title("title1")
                        .description("description1")
                        .image("image1")
                        .build());

        List<CakeEntity> cakeEntities = asList(
                CakeEntity.builder()
                        .title("title1")
                        .description("description1")
                        .image("image1")
                        .build(),
                CakeEntity.builder()
                        .title("title1")
                        .description("description1")
                        .image("image1")
                        .build());

        cakeService.saveCakes(cakes);

        verify(cakeRepository).saveAll(cakeEntities);
    }

    @Test
    public void shouldAddCake() {
        Cake cake = Cake.builder()
                .title("title1")
                .description("description1")
                .image("image1")
                .build();

        CakeEntity cakeEntity = CakeEntity.builder()
                .title("title1")
                .description("description1")
                .image("image1")
                .build();

        cakeService.addCake(cake);

        verify(cakeRepository).saveAll(singletonList(cakeEntity));
    }
}