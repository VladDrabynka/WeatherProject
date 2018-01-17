package com.netcracker.controller;

import com.netcracker.service.impl.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Path;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    @Autowired
    SchedulerService schedulerService;

    @GetMapping("/delay={fixedDelay}")
    public String setScheduleDelay(@PathVariable("fixedDelay") Integer fixedDelay){
        return "Schedule delay set to " + schedulerService.setFixedRate(fixedDelay.toString());
    }
}
