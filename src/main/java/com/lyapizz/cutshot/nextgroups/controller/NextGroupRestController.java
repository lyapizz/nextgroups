package com.lyapizz.cutshot.nextgroups.controller;

import java.io.IOException;
import java.util.List;

import com.lyapizz.cutshot.nextgroups.NextGroupsService;
import com.lyapizz.cutshot.nextgroups.model.Format;
import com.lyapizz.cutshot.nextgroups.model.GroupResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/nextGroups", produces = "application/json;charset=UTF-8")
public class NextGroupRestController {

    NextGroupsService nextGroupsService;

    @GetMapping
    @CrossOrigin(origins = "*")
    List<GroupResult> getNextGroups(@RequestParam(defaultValue = "https://cut-shot.ru/event/elagin-start") String tournament,
                                    @RequestParam(defaultValue = "") String surname,
                                    Format format) throws IOException {
        return nextGroupsService.calculateGroups(tournament, surname, format);
    }

}
