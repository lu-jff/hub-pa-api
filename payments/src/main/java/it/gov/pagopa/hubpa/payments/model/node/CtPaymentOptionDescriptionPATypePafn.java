package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CtPaymentOptionDescriptionPATypePafn
 */
@Validated
public class CtPaymentOptionDescriptionPATypePafn {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("options")
  private StAmountOptionTypePafn options = null;

  @JsonProperty("dueDate")
  private String dueDate = null;

  @JsonProperty("detailDescription")
  private String detailDescription = null;

  @JsonProperty("allCCP")
  private Boolean allCCP = null;

  public CtPaymentOptionDescriptionPATypePafn amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * 
   * @return amount
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
@Pattern(regexp="\\d+\\.\\d{2}") 
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public CtPaymentOptionDescriptionPATypePafn options(StAmountOptionTypePafn options) {
    this.options = options;
    return this;
  }

  /**
   * Get options
   * 
   * @return options
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public StAmountOptionTypePafn getOptions() {
    return options;
  }

  public void setOptions(StAmountOptionTypePafn options) {
    this.options = options;
  }

  public CtPaymentOptionDescriptionPATypePafn dueDate(String dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Get dueDate
   * 
   * @return dueDate
   **/
  @ApiModelProperty(value = "")

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public CtPaymentOptionDescriptionPATypePafn detailDescription(String detailDescription) {
    this.detailDescription = detailDescription;
    return this;
  }

  /**
   * Get detailDescription
   * 
   * @return detailDescription
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 140)
  public String getDetailDescription() {
    return detailDescription;
  }

  public void setDetailDescription(String detailDescription) {
    this.detailDescription = detailDescription;
  }

  public CtPaymentOptionDescriptionPATypePafn allCCP(Boolean allCCP) {
    this.allCCP = allCCP;
    return this;
  }

  /**
   * Get allCCP
   * 
   * @return allCCP
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public Boolean isAllCCP() {
    return allCCP;
  }

  public void setAllCCP(Boolean allCCP) {
    this.allCCP = allCCP;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtPaymentOptionDescriptionPATypePafn ctPaymentOptionDescriptionPATypePafn = (CtPaymentOptionDescriptionPATypePafn) o;
    return Objects.equals(this.amount, ctPaymentOptionDescriptionPATypePafn.amount)
        && Objects.equals(this.options, ctPaymentOptionDescriptionPATypePafn.options)
        && Objects.equals(this.dueDate, ctPaymentOptionDescriptionPATypePafn.dueDate)
        && Objects.equals(this.detailDescription, ctPaymentOptionDescriptionPATypePafn.detailDescription)
        && Objects.equals(this.allCCP, ctPaymentOptionDescriptionPATypePafn.allCCP);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, options, dueDate, detailDescription, allCCP);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtPaymentOptionDescriptionPATypePafn {\n");

    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    detailDescription: ").append(toIndentedString(detailDescription)).append("\n");
    sb.append("    allCCP: ").append(toIndentedString(allCCP)).append("\n");
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
