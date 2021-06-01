package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Input headers for wsdl operation paVerifyPaymentNoticeReq
 */
@ApiModel(description = "Input headers for wsdl operation paVerifyPaymentNoticeReq")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-01T12:59:52.852Z")

public class PaVerifyPaymentNoticeHeader {
  @JsonProperty("Security")
  private Security security = null;

  public PaVerifyPaymentNoticeHeader security(Security security) {
    this.security = security;
    return this;
  }

  /**
   * Get security
   * 
   * @return security
   **/
  @ApiModelProperty(value = "")

  @Valid

  public Security getSecurity() {
    return security;
  }

  public void setSecurity(Security security) {
    this.security = security;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaVerifyPaymentNoticeHeader paVerifyPaymentNoticeHeader = (PaVerifyPaymentNoticeHeader) o;
    return Objects.equals(this.security, paVerifyPaymentNoticeHeader.security);
  }

  @Override
  public int hashCode() {
    return Objects.hash(security);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaVerifyPaymentNoticeHeader {\n");

    sb.append("    security: ").append(toIndentedString(security)).append("\n");
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
