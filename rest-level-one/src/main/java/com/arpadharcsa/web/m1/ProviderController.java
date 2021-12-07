package com.arpadharcsa.web.m1;

import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix.m1}/" + ProviderController.RESOURCE_NAME)
public class ProviderController {
    static final String RESOURCE_NAME = "providers";
    static final String SUB_RESOURCE_NAME = "slots";
    private final AppointmentService appointmentService;

    public ProviderController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<List<Provider>> loadAvailableProviders() {
        return ResponseEntity.ok(appointmentService.getAllRegisteredProviders());
    }

    @PostMapping("{providerId}/slots")
    public ResponseEntity<List<Slot>> loadOpenSlots(@PathVariable final String providerId) {
        return ResponseEntity.ok(appointmentService.findOpenSlotsById(providerId));
    }

}
