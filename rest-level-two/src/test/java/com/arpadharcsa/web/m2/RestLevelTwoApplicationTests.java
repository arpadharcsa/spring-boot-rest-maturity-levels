package com.arpadharcsa.web.m2;

import com.arpadharcsa.web.m2.config.EnableApiMaturityLevelTwo;
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
@EnableApiMaturityLevelTwo
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "api.prefix.m2=" + RestLevelTwoApplicationTests.API_PREFIX)
class RestLevelTwoApplicationTests {
    static final String API_PREFIX = "m2";

    private static final String URI_FORMAT = "http://localhost:%d/" + API_PREFIX + "/%s";

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
        final String uri = URI_FORMAT.formatted(serverPort, ProviderController.RESOURCE_NAME);
        final Provider provider = new Provider(UUID.randomUUID().toString(), "testProvider");
        given(appointmentService.getAllRegisteredProviders()).willReturn(List.of(provider));

        //when
        var response = restTemplate.getForEntity(uri, Provider[].class);

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
        final String uri = URI_FORMAT.formatted(serverPort, ProviderController.RESOURCE_NAME);
        final Provider provider = new Provider(UUID.randomUUID().toString(), "testProvider");
        final Slot slot = new Slot(UUID.randomUUID().toString(),
                provider,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
        given(appointmentService.findOpenSlotsById(provider.id())).willReturn(List.of(slot));

        //when
        var response = restTemplate.getForEntity(uri + "/" + provider.id() + "/slots", Slot[].class);

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
        final String uri = URI_FORMAT.formatted(serverPort, AppointmentController.RESOURCE_NAME);
        final Provider provider = new Provider(UUID.randomUUID().toString(), "testProvider");
        final Customer testCustomer = new Customer(UUID.randomUUID().toString(), "testCustomer");
        final Slot slot = new Slot(UUID.randomUUID().toString(),
                provider,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1)
        );
        given(appointmentService.createAppointment(slot.id(), testCustomer)).willReturn(new Appointment(slot, testCustomer));

        //when
        var response = restTemplate.postForEntity(uri,
                new Appointment(slot, testCustomer),
                Void.class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);


    }
}
