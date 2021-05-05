package com.lyapizz.cutshot.nextgroups.controller;

import java.io.IOException;

import com.lyapizz.cutshot.nextgroups.NextGroupsService;
import com.lyapizz.cutshot.nextgroups.model.Format;
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
    public String getNextGroups(Model model, @RequestParam(defaultValue = "https://cut-shot.ru/event/elagin-start") String tournament,
                                @RequestParam(defaultValue = "") String surname,
                                Format format) throws IOException {
        model.addAttribute("nextGroups", nextGroupsService.calculateGroups(tournament, surname, format));
        return "nextGroups";
    }

}
