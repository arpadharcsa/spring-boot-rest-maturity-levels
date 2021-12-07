package com.arpadharcsa.web.m0;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix.m0}/" + AppointmentController.SERVICE_NAME)
public class AppointmentController {
    static final String SERVICE_NAME = "appointmentservice";
    static final String AVAILABLE_PROVIDERS = "availableproviders";
    static final String OPEN_SLOTS = "openslots";
    static final String CREATE_APPOINTMENT = "createappointment";

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(AVAILABLE_PROVIDERS)
    public ResponseEntity<List<Provider>> loadAvailableProviders() {
        return ResponseEntity.ok(appointmentService.getAllRegisteredProviders());
    }

    @PostMapping(OPEN_SLOTS)
    public ResponseEntity<List<Slot>> loadOpenSlots(@RequestBody final OpenSlotRequest request) {
        return ResponseEntity.ok(appointmentService.findOpenSlotsById(request.providerId()));
    }

    @PostMapping(CREATE_APPOINTMENT)
    public ResponseEntity<Appointment> createAppointment(@RequestBody final CreateAppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request.slotId(), request.customer()));
    }

}
