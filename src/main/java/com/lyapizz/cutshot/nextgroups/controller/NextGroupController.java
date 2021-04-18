package com.lyapizz.cutshot.nextgroups.controller;

import java.io.IOException;

import com.lyapizz.cutshot.nextgroups.NextGroupsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping(value = "nextGroups")
public class NextGroupController {

    NextGroupsService nextGroupsService;

    @GetMapping
    public String getNextGroups(Model model, @RequestParam(defaultValue = "https://cut-shot.ru/event/spring-cup-4#zayavki") String tournament,
                                @RequestParam(defaultValue = "lepexin") String surname) throws IOException {
        model.addAttribute("nextGroups", nextGroupsService.calculateGroups(tournament, surname));
        return "nextGroups";
    }

}
