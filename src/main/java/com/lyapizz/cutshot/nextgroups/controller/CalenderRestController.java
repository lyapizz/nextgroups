package com.lyapizz.cutshot.nextgroups.controller;

import java.io.IOException;

import com.lyapizz.cutshot.nextgroups.model.Tournament;
import com.lyapizz.cutshot.nextgroups.model.response.CalenderResponse;
import com.lyapizz.cutshot.nextgroups.service.CalenderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "internal/calender", produces = "application/json;charset=UTF-8")

public class CalenderRestController {

    CalenderService calenderService;

    @GetMapping
    CalenderResponse getCalender() throws IOException {
        return calenderService.getCalender();
    }

}
