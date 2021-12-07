package com.arpadharcsa.service;

import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Customer;
import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;

import java.util.List;

public interface AppointmentService {

    List<Provider> getAllRegisteredProviders();

    List<Slot> findOpenSlotsById(String providerId);

    Appointment createAppointment(String slotId, Customer customer);
}
