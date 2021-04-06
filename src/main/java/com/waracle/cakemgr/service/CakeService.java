package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.repository.CakeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@AllArgsConstructor
public class CakeService {

    private final CakeRepository cakeRepository;

    public void saveCakes(Collection<Cake> cakes) {
        Collection<CakeEntity> cakeEntities = cakes.stream()
            .map(this::toCakeEntity)
            .collect(toSet());

        cakeRepository.saveAll(cakeEntities);
    }

    public Collection<Cake> getAllCakes() {
        Collection<CakeEntity> cakes = cakeRepository.findAll();
        return cakes.stream()
                .map(this::toCake)
                .collect(toList());
    }

    public void addCake(Cake cake) {
        saveCakes(singletonList(cake));
    }

    private Cake toCake(CakeEntity cakeEntity) {
        return Cake.builder()
                .title(cakeEntity.getTitle())
                .description(cakeEntity.getDescription())
                .image(cakeEntity.getImage())
                .build();
    }

    private CakeEntity toCakeEntity(Cake cake) {
        return CakeEntity.builder()
                .title(cake.getTitle())
                .description(cake.getDescription())
                .image(cake.getImage())
                .build();
    }
}
