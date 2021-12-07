package com.arpadharcsa.web.m3.config;

import com.arpadharcsa.service.AppointmentService;
import com.arpadharcsa.web.m3.AppointmentController;
import com.arpadharcsa.web.m3.ProviderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApiConfig {

    @Bean
    AppointmentController getAppointmentV3Controller(final AppointmentService appointmentService){
        return new AppointmentController(appointmentService);
    }

    @Bean
    ProviderController getProviderV3Controller(final AppointmentService appointmentService){
        return new ProviderController(appointmentService);
    }
}
