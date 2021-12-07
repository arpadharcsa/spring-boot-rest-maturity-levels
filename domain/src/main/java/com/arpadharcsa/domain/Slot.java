package com.arpadharcsa.domain;

import java.time.LocalDateTime;

public record Slot(String id, Provider provider, LocalDateTime from, LocalDateTime to) {
}
