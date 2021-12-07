package com.arpadharcsa.web.m3;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;
import com.arpadharcsa.service.AppointmentService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(ProviderController.RESOURCE_NAME)
public class ProviderController {
    static final String RESOURCE_NAME = "providers";
    static final String SUB_RESOURCE_NAME = "slots";
    private final AppointmentService appointmentService;

    public ProviderController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Provider>> loadAvailableProviders() {
        var items = appointmentService.getAllRegisteredProviders().stream()
                .map(provider -> EntityModel.of(provider,
                        linkTo(methodOn(ProviderController.class).loadOpenSlots(provider.id())).withRel(SUB_RESOURCE_NAME)))
                .toList();
        return CollectionModel.of(items,
                linkTo(methodOn(ProviderController.class).loadAvailableProviders()).withSelfRel()
        );
    }

    @GetMapping("{providerId}/" + SUB_RESOURCE_NAME)
    public CollectionModel<EntityModel<Slot>> loadOpenSlots(@PathVariable final String providerId) {
        final var availableSlots = appointmentService.findOpenSlotsById(providerId).stream()
                .map(slot -> EntityModel.of(slot,
                        linkTo(methodOn(AppointmentController.class).createAppointment(null)).withRel(AppointmentController.RESOURCE_NAME)))
                .toList();
        return CollectionModel.of(availableSlots,
                linkTo(methodOn(ProviderController.class).loadOpenSlots(providerId)).withSelfRel()
        );
    }

}
