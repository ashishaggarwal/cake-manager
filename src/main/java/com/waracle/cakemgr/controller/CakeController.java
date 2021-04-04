package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.dto.Cake;
import com.waracle.cakemgr.service.CakeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@AllArgsConstructor
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @GetMapping(value = "/")
    public String cakes(Model model) {
        List<Cake> cakes = cakeService.getAllCakes();
        model.addAttribute("cakes", cakes);
        return "cake";
    }

    @GetMapping(value = "/cakes", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Cake> cakesJson() {
        return cakeService.getAllCakes();
    }

    @PostMapping(value = "/cakes")
    public String addCake(@RequestBody Cake cake) {
        cakeService.addCake(cake);
        return "cake";
    }
}
