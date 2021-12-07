package com.arpadharcsa.web.m0.config;

import com.arpadharcsa.service.AppointmentService;
import com.arpadharcsa.web.m0.AppointmentController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("v0config")
class ApiConfig {

    @Bean
    AppointmentController getAppointmentV0Controller(final AppointmentService appointmentService){
        return new AppointmentController(appointmentService);
    }
}
