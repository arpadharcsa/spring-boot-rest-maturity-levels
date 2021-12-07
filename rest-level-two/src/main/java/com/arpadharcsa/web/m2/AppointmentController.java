package com.arpadharcsa.web.m2;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Customer;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;

@RestController
@RequestMapping("${api.prefix.m2}/" + AppointmentController.RESOURCE_NAME)
public class AppointmentController {
    static final String RESOURCE_NAME = "appointments";

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody final Appointment appointment) {
        appointmentService.createAppointment(appointment.slot().id(), appointment.customer());
        return ResponseEntity.created(URI.create("the/new/resource/available/here"))
                .build();
    }

}
