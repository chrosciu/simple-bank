package eu.chrost.simplebank.transfer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {
    @MockBean
    TransferService transferService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnOkStatusIfTransferSucceed() throws Exception {
        var fromAccountId = 1L;
        var toAccountId = 2L;
        var amount = BigDecimal.valueOf(1200);

        var requestBody = """
                {
                    "fromAccountId": "1",
                    "toAccountId": "2",
                    "amount": 1200
                }
                """;

        doNothing()
                .when(transferService)
                .makeTransfer(fromAccountId, toAccountId, amount);

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(transferService).makeTransfer(fromAccountId, toAccountId, amount);
    }

    @Test
    void shouldReturnBadRequestStatusIfTransferFails() throws Exception {
        var fromAccountId = 1L;
        var toAccountId = 2L;
        var amount = BigDecimal.valueOf(1200);

        var requestBody = """
                {
                    "fromAccountId": "1",
                    "toAccountId": "2",
                    "amount": 1200
                }
                """;

        doThrow(new IllegalStateException("Cannot make transfer"))
                .when(transferService)
                .makeTransfer(fromAccountId, toAccountId, amount);

        mockMvc.perform(MockMvcRequestBuilders.post("/transfer").content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is("Cannot make transfer")));
    }
}
