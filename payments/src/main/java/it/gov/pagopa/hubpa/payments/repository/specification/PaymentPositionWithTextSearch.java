package it.gov.pagopa.hubpa.payments.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentPositionWithTextSearch implements Specification<PaymentPosition> {

    private static final long serialVersionUID = 4100418988221955504L;

    private String textSearch;

    @Override
    public Predicate toPredicate(Root<PaymentPosition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	if (textSearch == null || textSearch.isEmpty()) {
	    return cb.isTrue(cb.literal(true)); // always true = no filtering
	}
	query.distinct(true);
	char firstChar=textSearch.charAt(0);
	if(Character.isDigit(firstChar)) {
	    return cb.like(root.join("paymentOptions").get("notificationCode"), this.textSearch+"%");
	}else {
	    return cb.like(root.join("debitor").get("fiscalCode"), this.textSearch+"%");
	}
    }

}
