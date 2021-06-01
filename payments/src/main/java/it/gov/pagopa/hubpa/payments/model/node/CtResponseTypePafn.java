package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CtResponseTypePafn
 */
@Validated
public class CtResponseTypePafn {
  @JsonProperty("outcome")
  private StOutcomeTypePafn outcome = null;

  @JsonProperty("fault")
  private CtFaultBeanTypePafn fault = null;

  public CtResponseTypePafn outcome(StOutcomeTypePafn outcome) {
    this.outcome = outcome;
    return this;
  }

  /**
   * Get outcome
   * 
   * @return outcome
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public StOutcomeTypePafn getOutcome() {
    return outcome;
  }

  public void setOutcome(StOutcomeTypePafn outcome) {
    this.outcome = outcome;
  }

  public CtResponseTypePafn fault(CtFaultBeanTypePafn fault) {
    this.fault = fault;
    return this;
  }

  /**
   * Get fault
   * 
   * @return fault
   **/
  @ApiModelProperty(value = "")

  @Valid

  public CtFaultBeanTypePafn getFault() {
    return fault;
  }

  public void setFault(CtFaultBeanTypePafn fault) {
    this.fault = fault;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtResponseTypePafn ctResponseTypePafn = (CtResponseTypePafn) o;
    return Objects.equals(this.outcome, ctResponseTypePafn.outcome)
        && Objects.equals(this.fault, ctResponseTypePafn.fault);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outcome, fault);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtResponseTypePafn {\n");

    sb.append("    outcome: ").append(toIndentedString(outcome)).append("\n");
    sb.append("    fault: ").append(toIndentedString(fault)).append("\n");
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
