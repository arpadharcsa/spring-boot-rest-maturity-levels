package com.arpadharcsa.config;

import com.arpadharcsa.web.m0.config.EnableApiMaturityLevelZero;
import com.arpadharcsa.web.m1.config.EnableApiMaturityLevelOne;
import com.arpadharcsa.service.AppointmentService;
import com.arpadharcsa.service.DefaultAppointmentService;
import com.arpadharcsa.web.m2.config.EnableApiMaturityLevelTwo;
import com.arpadharcsa.web.m3.config.EnableApiMaturityLevelThree;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApiMaturityLevelZero
@EnableApiMaturityLevelOne
@EnableApiMaturityLevelTwo
@EnableApiMaturityLevelThree
public class AppConfig {

    @Bean
    AppointmentService getAppointmentService() {
        return new DefaultAppointmentService();
    }
}
