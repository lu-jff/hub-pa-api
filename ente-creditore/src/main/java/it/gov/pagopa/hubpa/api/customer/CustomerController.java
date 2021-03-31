package it.gov.pagopa.hubpa.api.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class CustomerController {

  @Autowired
  private CustomerRepository customerRepository;

  @GetMapping("/customers")
  public Page<Customer> getCustomers(Pageable pageable) {
    return customerRepository.findAll(pageable);
  }

  @PostMapping("/customers")
  public Customer createCustomer(@Valid @RequestBody Customer customer) {
    return customerRepository.save(customer);
  }

}