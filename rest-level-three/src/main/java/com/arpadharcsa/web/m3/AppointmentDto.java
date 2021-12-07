package com.arpadharcsa.web.m3;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Slot;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class AppointmentDto extends RepresentationModel<AppointmentDto> {
    private final String id;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final String provider;
    private final String customer;

    public static AppointmentDto of(Appointment ap) {
        final Slot slot = ap.slot();
        return new AppointmentDto(
                slot.id(),
                slot.from(),
                slot.to(),
                slot.provider().name(),
                ap.customer().name()
        );
    }

    private AppointmentDto(String id, LocalDateTime from, LocalDateTime to, String provider, String customer) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.provider = provider;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getProvider() {
        return provider;
    }

    public String getCustomer() {
        return customer;
    }
}
