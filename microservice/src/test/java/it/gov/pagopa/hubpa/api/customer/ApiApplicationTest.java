package it.gov.pagopa.hubpa.api.customer;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

@WebMvcTest
class ApiApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerRepository customerRepository;

  @Test
  public void shouldGetCustomers() throws Exception {
    this.mockMvc.perform(get("/customers")).andExpect(status().isOk());
  }

  @Test
  public void shouldPostCustomers() throws Exception {
    this.mockMvc.perform(post("/customers")).andExpect(status().is(400));
  }
}
