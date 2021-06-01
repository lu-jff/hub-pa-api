package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * Input message for wsdl operation paGetPaymentReq
 */
@ApiModel(description = "Input message for wsdl operation paGetPaymentReq")
@Validated
public class PaGetPaymentInput {
  @JsonProperty("Envelope")
  private Object envelope = null;

  public PaGetPaymentInput envelope(Object envelope) {
    this.envelope = envelope;
    return this;
  }

  /**
   * Get envelope
   * 
   * @return envelope
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  public Object getEnvelope() {
    return envelope;
  }

  public void setEnvelope(Object envelope) {
    this.envelope = envelope;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaGetPaymentInput paGetPaymentInput = (PaGetPaymentInput) o;
    return Objects.equals(this.envelope, paGetPaymentInput.envelope);
  }

  @Override
  public int hashCode() {
    return Objects.hash(envelope);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaGetPaymentInput {\n");

    sb.append("    envelope: ").append(toIndentedString(envelope)).append("\n");
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
