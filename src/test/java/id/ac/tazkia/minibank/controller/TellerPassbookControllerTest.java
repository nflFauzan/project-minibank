package id.ac.tazkia.minibank.controller;
 
import id.ac.tazkia.minibank.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = TellerPassbookController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("TellerPassbookController Unit Tests")
class TellerPassbookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @org.springframework.boot.test.mock.mockito.MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("GET /teller/passbook/select-account - should return view")
    void selectAccount_shouldReturnView() throws Exception {
        mockMvc.perform(get("/teller/passbook/select-account"))
                .andExpect(status().isOk())
                .andExpect(view().name("teller/passbook/select-account"));
    }
}
