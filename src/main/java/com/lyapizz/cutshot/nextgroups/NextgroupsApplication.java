package com.lyapizz.cutshot.nextgroups;

import com.lyapizz.cutshot.nextgroups.config.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfiguration.class)
public class NextgroupsApplication {

    private static final Logger LOG = LoggerFactory.getLogger(NextgroupsApplication.class);

    private final NextGroupsService nextGroupsService;

    public NextgroupsApplication(NextGroupsService nextGroupsService) {
        this.nextGroupsService = nextGroupsService;
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            LOG.info("Starting CLR application");
            nextGroupsService.printGroups();
            LOG.info("Finishing CLR application");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(NextgroupsApplication.class, args);
    }

}
