package eu.chrost.simplebank.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnAccountForGivenId() throws Exception {
        var someId = 1L;
        var someAccount = Account.builder().id(1L).balance(BigDecimal.valueOf(1000)).build();
        doReturn(Optional.of(someAccount)).when(accountRepository).findById(someId);

        mockMvc.perform(MockMvcRequestBuilders.get("/account/{id}", someId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance", is(1000)));
    }
}
