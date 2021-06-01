package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Structure containing the details of possible payments relating to the debt
 * position to be paid. Currently set at 5 eligible payments per single
 * position. Where each &#x60;paymentOptionDescription&#x60; items contains : -
 * &#x60;amount&#x60; : payment amount - &#x60;options&#x60; : indicates the
 * payment criteria accepted by the institution with respect to the amount, or
 * if it accepts for this payment option other than &#x60;amount&#x60;. -
 * &#x60;dueDate&#x60; : indicates the expiration payment date according to the
 * ISO 8601 format &#x60;[YYYY]-[MM]-[DD]&#x60;. - &#x60;detailDescription&#x60;
 * : Free text available to describe the payment reasons - &#x60;allCCP&#x60; :
 * indicates that all transfers are associable to all postal IBAN
 */
@ApiModel(description = "Structure containing the details of possible payments relating to the debt position to be paid.  Currently set at 5 eligible payments per single position.  Where each `paymentOptionDescription` items contains :  - `amount` : payment amount - `options` : indicates the payment criteria accepted by the institution with respect to the amount, or if it accepts for this payment option other than `amount`. - `dueDate` : indicates the expiration payment date according to the ISO 8601 format `[YYYY]-[MM]-[DD]`. - `detailDescription` : Free text available to describe the payment reasons - `allCCP` : indicates that all transfers are associable to all postal IBAN")
@Validated
public class CtPaymentOptionsDescriptionListPATypePafn {
  @JsonProperty("paymentOptionDescription")
  @Valid
  private List<CtPaymentOptionDescriptionPATypePafn> paymentOptionDescription = new ArrayList<CtPaymentOptionDescriptionPATypePafn>();

  public CtPaymentOptionsDescriptionListPATypePafn paymentOptionDescription(
      List<CtPaymentOptionDescriptionPATypePafn> paymentOptionDescription) {
    this.paymentOptionDescription = paymentOptionDescription;
    return this;
  }

  public CtPaymentOptionsDescriptionListPATypePafn addPaymentOptionDescriptionItem(
      CtPaymentOptionDescriptionPATypePafn paymentOptionDescriptionItem) {
    this.paymentOptionDescription.add(paymentOptionDescriptionItem);
    return this;
  }

  /**
   * Get paymentOptionDescription
   * 
   * @return paymentOptionDescription
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  @Size(min = 1, max = 5)
  public List<CtPaymentOptionDescriptionPATypePafn> getPaymentOptionDescription() {
    return paymentOptionDescription;
  }

  public void setPaymentOptionDescription(List<CtPaymentOptionDescriptionPATypePafn> paymentOptionDescription) {
    this.paymentOptionDescription = paymentOptionDescription;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtPaymentOptionsDescriptionListPATypePafn ctPaymentOptionsDescriptionListPATypePafn = (CtPaymentOptionsDescriptionListPATypePafn) o;
    return Objects.equals(this.paymentOptionDescription,
        ctPaymentOptionsDescriptionListPATypePafn.paymentOptionDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentOptionDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtPaymentOptionsDescriptionListPATypePafn {\n");

    sb.append("    paymentOptionDescription: ").append(toIndentedString(paymentOptionDescription)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
