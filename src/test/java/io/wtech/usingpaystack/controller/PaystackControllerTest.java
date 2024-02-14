package io.wtech.usingpaystack.controller;

import io.wtech.usingpaystack.dto.CreatePlanDto;
import io.wtech.usingpaystack.dto.InitializePaymentDto;
import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.response.CreatePlanResponse;
import io.wtech.usingpaystack.response.InitializePaymentResponse;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;
import io.wtech.usingpaystack.service.api.PaystackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;

@MockBean({PaystackService.class})
@WebMvcTest(controllers = PaystackController.class)
class PaystackControllerTest {
    @Autowired
    private PaystackService paystackService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void createPlan() throws Exception {
        final CreatePlanDto testDto = CreatePlanDto.builder()
                .name("Ifeanyichukwu Otiwa")
                .amount(1000000)
                .interval("monthly")
                .build();

        final CreatePlanResponse expectedResponse = CreatePlanResponse.builder()
                .status(Boolean.FALSE)
                .message("Plan creation failed")
                .data(null)
                .build();
        when(paystackService.createPlan(testDto))
                .thenReturn(expectedResponse);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/payment/createplan")
                                .contentType("application/json")
                                .content("""
                                        {
                                           "name": "Ifeanyichukwu Otiwa",
                                           "interval": "monthly",
                                           "amount": 1000000
                                         }
                                         """
                                )

                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Plan creation failed"));
    }

    @Test
    void initializePayment() throws Exception {
        final InitializePaymentDto testDto = InitializePaymentDto.builder()
                .amount("1000.0")
                .email("test@example.com")
                .currency("USD")
                .plan("BASIC")
                .channels(new String[]{"USSD"})
                .build();

        final InitializePaymentResponse expectedResponse = InitializePaymentResponse.builder()
                .status(Boolean.FALSE)
                .message("Payment initialization failed")
                .data(null)
                .build();

        when(paystackService.initializePayment(testDto))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/payment/initializepayment")
                               .contentType("application/json")
                               .content("""
                                        {
                                           "amount": "1000.0",
                                           "email": "test@example.com",
                                           "currency": "USD",
                                           "plan": "BASIC",
                                           "channels": ["USSD"]
                                         }
                                         """
                                )
                )
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment initialization failed"));
    }

    @Test
    void paymentVerification() throws Exception {
        final String reference = "reference";
        final String plan = "BASIC";
        final UUID id = UUID.randomUUID();
        final PaymentVerificationDto testDto = new PaymentVerificationDto(reference, plan, id);
        final PaymentVerificationResponse expectedResponse = PaymentVerificationResponse.builder()
                .status("false")
                .message("Payment verification failed")
                .data(null)
                .build();
        when(paystackService.paymentVerification(testDto))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/payment/verifypayment/{reference}/{plan}/{id}", reference, plan, id)
                        .accept("application/json")
        )
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Payment verification failed"));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void paymentVerification_with_invalid_plan(String nullPlan) throws Exception {
        final String reference = "reference";
        final UUID id = UUID.randomUUID();

        mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/payment/verifypayment/{reference}/{plan}/{id}", reference, nullPlan, id)
                           .accept("application/json")
            )
               .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    @Test
    void paymentVerification_with_invalid_plan() throws Exception {
        final String reference = "reference";
        final UUID id = UUID.randomUUID();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/payment/verifypayment/{reference}/{plan}/{id}", reference, "", id)
                                .accept("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void paymentVerification_with_invalid_reference(final String nullReference) throws Exception {
        final UUID id = UUID.randomUUID();
        final String plan = "BASIC";

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/payment/verifypayment/{reference}/{plan}/{id}", nullReference, plan, id)
                                .accept("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }


    @Test
    void paymentVerification_with_invalid_reference() throws Exception {
        final UUID id = UUID.randomUUID();
        final String plan = "BASIC";

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/payment/verifypayment/{reference}/{plan}/{id}", "", plan, id)
                                .accept("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }


}