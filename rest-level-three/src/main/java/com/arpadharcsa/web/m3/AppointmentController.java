package com.arpadharcsa.web.m3;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(AppointmentController.RESOURCE_NAME)
public class AppointmentController {
    static final String RESOURCE_NAME = "appointments";

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody final Appointment appointment) {
        var ap = appointmentService.createAppointment(appointment.slot().id(), appointment.customer());
        var dto = AppointmentDto.of(ap);
        dto.add(linkTo(AppointmentController.class).withSelfRel());
        return ResponseEntity.created(
                linkTo(AppointmentController.class).toUri()
        ).body(dto);
    }

}
