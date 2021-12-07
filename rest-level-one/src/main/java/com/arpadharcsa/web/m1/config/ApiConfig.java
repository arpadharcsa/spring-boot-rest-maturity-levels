package com.arpadharcsa.web.m1.config;

import com.arpadharcsa.service.AppointmentService;
import com.arpadharcsa.web.m1.AppointmentController;
import com.arpadharcsa.web.m1.ProviderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApiConfig {

    @Bean
    AppointmentController getAppointmentV1Controller(final AppointmentService appointmentService){
        return new AppointmentController(appointmentService);
    }

    @Bean
    ProviderController getProviderV1Controller(final AppointmentService appointmentService){
        return new ProviderController(appointmentService);
    }
}
