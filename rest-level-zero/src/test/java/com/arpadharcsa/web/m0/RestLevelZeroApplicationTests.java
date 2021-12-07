package com.arpadharcsa.web.m0;

import com.arpadharcsa.web.m0.config.EnableApiMaturityLevelZero;
import com.arpadharcsa.domain.Appointment;
import com.arpadharcsa.domain.Customer;
import com.arpadharcsa.domain.Provider;
import com.arpadharcsa.domain.Slot;
import com.arpadharcsa.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableApiMaturityLevelZero
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "api.prefix.m0=" + RestLevelZeroApplicationTests.API_PREFIX)
class RestLevelZeroApplicationTests {
    static final String API_PREFIX = "m0";

    private static final String URI_FORMAT = "http://localhost:%d/" + API_PREFIX + "/%s/%s";

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private long serverPort;

    @MockBean
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadProviders() {
        //given
        final String uri = URI_FORMAT.formatted(serverPort,
                AppointmentController.SERVICE_NAME,
                AppointmentController.AVAILABLE_PROVIDERS
        );
        final Provider provider = new Provider(UUID.randomUUID().toString(), "testProvider");
        given(appointmentService.getAllRegisteredProviders()).willReturn(List.of(provider));

        //when
        var response = restTemplate.postForEntity(uri, null, Provider[].class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Provider[] providers = response.getBody();
        assertThat(providers).isNotNull();
        assertThat(providers.length).isOne();
        assertThat(providers[0]).isEqualTo(provider);
    }

    @Test
    void loadOpenSlots() {
        //given
        final String uri = URI_FORMAT.formatted(serverPort,
                AppointmentController.SERVICE_NAME,
                AppointmentController.OPEN_SLOTS
        );
        final String providerId = UUID.randomUUID().toString();
        final Provider provider = new Provider(providerId, "testProvider");

        final Slot slot = new Slot(UUID.randomUUID().toString(),
                provider,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
        given(appointmentService.findOpenSlotsById(providerId)).willReturn(List.of(slot));

        //when
        var response = restTemplate.postForEntity(uri,
                new OpenSlotRequest(providerId),
                Slot[].class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Slot[] slots = response.getBody();
        assertThat(slots).isNotNull();
        assertThat(slots.length).isOne();
        assertThat(slots[0]).isEqualTo(slot);
    }

    @Test
    void reserveAnAppointment() {
        //given
        final String uri = URI_FORMAT.formatted(serverPort,
                AppointmentController.SERVICE_NAME,
                AppointmentController.CREATE_APPOINTMENT
        );
        final Provider provider = new Provider(UUID.randomUUID().toString(), "testProvider");
        final Customer testCustomer = new Customer(UUID.randomUUID().toString(), "testCustomer");
        final String slotId = UUID.randomUUID().toString();
        final Slot slot = new Slot(slotId,
                provider,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
        given(appointmentService.createAppointment(slotId, testCustomer)).willReturn(new Appointment(slot, testCustomer));

        //when
        var response = restTemplate.postForEntity(uri,
                new CreateAppointmentRequest(slotId, testCustomer),
                Appointment.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Appointment appointment = response.getBody();
        assertThat(appointment).isNotNull();
        assertThat(appointment.customer()).isEqualTo(testCustomer);
        assertThat(appointment.slot()).isEqualTo(slot);
    }
}
