package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * Output message for wsdl operation paSendRTReq
 */
@ApiModel(description = "Output message for wsdl operation paSendRTReq")
@Validated
public class PaSendRTOutput {
  @JsonProperty("Envelope")
  private Object envelope = null;

  public PaSendRTOutput envelope(Object envelope) {
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
    PaSendRTOutput paSendRTOutput = (PaSendRTOutput) o;
    return Objects.equals(this.envelope, paSendRTOutput.envelope);
  }

  @Override
  public int hashCode() {
    return Objects.hash(envelope);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaSendRTOutput {\n");

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
