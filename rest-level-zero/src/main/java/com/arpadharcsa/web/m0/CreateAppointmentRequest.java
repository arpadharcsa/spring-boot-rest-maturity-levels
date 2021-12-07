package com.arpadharcsa.web.m0;

import com.arpadharcsa.domain.Customer;

record CreateAppointmentRequest(String slotId, Customer customer) {
}