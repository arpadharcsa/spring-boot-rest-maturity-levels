package com.arpadharcsa.service;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Customer;
import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class DefaultAppointmentService implements AppointmentService, InitializingBean {

    private List<Slot> slots;
    private List<Provider> providers;

    @Override
    public List<Slot> findOpenSlotsById(String providerId) {
        return slots.stream()
                .filter(s -> s.provider().id().equals(providerId))
                .toList();
    }

    @Override
    public Appointment createAppointment(String slotId, Customer customer) {
        final Slot reservedSlot = slots.stream()
                .filter(s -> Objects.equals(s.id(), slotId))
                .findFirst()
                .orElseThrow();
        return new Appointment(reservedSlot, customer);
    }

    @Override
    public List<Provider> getAllRegisteredProviders() {
        return providers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        providers = List.of(new Provider(UUID.randomUUID().toString(), "Provider"));
        slots = IntStream.range(8, 17)
                .mapToObj(i -> {
                    var from = LocalDateTime.of(LocalDate.now(), LocalTime.of(i, 0));
                    var to = from.plusHours(1);
                    return new Slot(UUID.randomUUID().toString(), providers.get(0), from, to);
                }).toList();
    }
}
