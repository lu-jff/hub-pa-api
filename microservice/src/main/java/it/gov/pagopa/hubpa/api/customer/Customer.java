package it.gov.pagopa.hubpa.api.customer;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator", sequenceName = "customer_sequence", initialValue = 666)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String myFirstName) {
        this.firstName = myFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String mylastName) {
        this.lastName = mylastName;
    }
}
