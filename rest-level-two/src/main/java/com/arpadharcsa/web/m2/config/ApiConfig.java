package com.arpadharcsa.web.m2.config;

import com.arpadharcsa.service.AppointmentService;
import com.arpadharcsa.web.m2.AppointmentController;
import com.arpadharcsa.web.m2.ProviderController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApiConfig {

    @Bean
    AppointmentController getAppointmentV2Controller(final AppointmentService appointmentService){
        return new AppointmentController(appointmentService);
    }

    @Bean
    ProviderController getProviderV2Controller(final AppointmentService appointmentService){
        return new ProviderController(appointmentService);
    }
}
