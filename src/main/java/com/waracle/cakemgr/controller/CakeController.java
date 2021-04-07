package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.service.CakeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@AllArgsConstructor
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @GetMapping(value = "/")
    public String cakes(Model model) {
        Collection<Cake> cakes = cakeService.getAllCakes();
        model.addAttribute("cakes", cakes);
        return "cake";
    }

    @GetMapping(value = "/cakes", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<Cake> cakesJson() {
        return cakeService.getAllCakes();
    }

    @PostMapping(value = "/cakes")
    @ResponseBody
    public ResponseEntity<Cake> addCake(@RequestBody Cake cake) {
        cakeService.addCake(cake);
        return new ResponseEntity<>(cake, CREATED);
    }

    @GetMapping(value = "/addCake")
    public String addCakeForm(Model model) {
        model.addAttribute("cake", Cake.builder().build());
        return "addCake";
    }

    @PostMapping(value = "/addCake", consumes = {APPLICATION_FORM_URLENCODED_VALUE})
    public String saveCake(Cake cake) {
        cakeService.addCake(cake);
        return "redirect:/";
    }
}
