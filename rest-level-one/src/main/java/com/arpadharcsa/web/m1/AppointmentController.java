package com.arpadharcsa.web.m1;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Customer;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix.m1}/" + AppointmentController.RESOURCE_NAME)
public class AppointmentController {
    static final String RESOURCE_NAME = "appointments";

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("{slotId}")
    public ResponseEntity<Appointment> createAppointment(@PathVariable final String slotId, @RequestBody final Customer customer) {
        return ResponseEntity.ok(appointmentService.createAppointment(slotId, customer));
    }

}
