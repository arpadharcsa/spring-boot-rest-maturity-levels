package com.arpadharcsa.web.m2;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Customer;
import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix.m2}/" + ProviderController.RESOURCE_NAME)
public class ProviderController {
    static final String RESOURCE_NAME = "providers";
    static final String SUB_RESOURCE_NAME = "slots";
    private final AppointmentService appointmentService;

    public ProviderController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Provider>> loadAvailableProviders() {
        return ResponseEntity.ok(appointmentService.getAllRegisteredProviders());
    }

    @GetMapping("{providerId}/" + SUB_RESOURCE_NAME)
    public ResponseEntity<List<Slot>> loadOpenSlots(@PathVariable final String providerId) {
        return ResponseEntity.ok(appointmentService.findOpenSlotsById(providerId));
    }

}
