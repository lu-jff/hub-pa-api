package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Its a response to &#x60;paGetPaymentReq&#x60; and contains : -
 * &#x60;outcome&#x60; and _optional_ &#x60;fault&#x60; (_see below to details_)
 * - all &#x60;data&#x60; related to payment (_see below to details_)
 */
@ApiModel(description = "Its a response to `paGetPaymentReq` and contains :  - `outcome` and _optional_ `fault` (_see below to details_) - all `data` related to payment (_see below to details_)")
@Validated
public class PaGetPaymentResElementPafn extends CtResponseTypePafn {
  @JsonProperty("data")
  private CtPaymentPATypePafn data = null;

  public PaGetPaymentResElementPafn data(CtPaymentPATypePafn data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * 
   * @return data
   **/
  @ApiModelProperty(value = "")

  @Valid

  public CtPaymentPATypePafn getData() {
    return data;
  }

  public void setData(CtPaymentPATypePafn data) {
    this.data = data;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaGetPaymentResElementPafn paGetPaymentResElementPafn = (PaGetPaymentResElementPafn) o;
    return Objects.equals(this.data, paGetPaymentResElementPafn.data) && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaGetPaymentResElementPafn {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
