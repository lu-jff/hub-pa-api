package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Its contains all payment information : - &#x60;creditorReferenceId&#x60; :
 * its equal to **IUV** _Identificativo Univoco Versamento_ -
 * &#x60;paymentAmount&#x60; : amount, it must be equal to the sums of
 * &#x60;transferAmount&#x60; present in the &#x60;transferList&#x60; -
 * &#x60;dueDate&#x60; : indicates the expiration payment date according to the
 * ISO 8601 format &#x60;[YYYY]-[MM]-[DD]&#x60;. - &#x60;retentionDate&#x60; :
 * indicates the retention payment date according to the ISO 8601 format
 * &#x60;[YYYY]-[MM]-[DD]&#x60;. - &#x60;lastPayment&#x60; : boolean flag used
 * for in installment payments - &#x60;description&#x60; : free text available
 * to describe the payment reasons - &#x60;companyName&#x60; : Public
 * Administration full name - &#x60;officeName&#x60; : Public Admninistration
 * Department Name - &#x60;debtor&#x60; : identifies the debtor to whom the debt
 * position refers - &#x60;transferList&#x60; : the list of all available
 * transfer information (_see below to details_) - &#x60;metadata&#x60; : (_see
 * below to details_)
 */
@ApiModel(description = "Its contains all payment information :  - `creditorReferenceId` : its equal to **IUV** _Identificativo Univoco Versamento_  - `paymentAmount` : amount, it must be equal to the sums of `transferAmount` present in the `transferList` - `dueDate` : indicates the expiration payment date according to the ISO 8601 format `[YYYY]-[MM]-[DD]`. - `retentionDate` : indicates the retention payment date according to the ISO 8601 format `[YYYY]-[MM]-[DD]`. - `lastPayment` : boolean flag used for in installment payments  - `description` : free text available to describe the payment reasons - `companyName` : Public Administration full name - `officeName` : Public Admninistration Department Name - `debtor` : identifies the debtor to whom the debt position refers - `transferList` : the list of all available transfer information (_see below to details_) - `metadata` : (_see below to details_)")
@Validated
public class CtPaymentPATypePafn {
  @JsonProperty("creditorReferenceId")
  private String creditorReferenceId = null;

  @JsonProperty("paymentAmount")
  private BigDecimal paymentAmount = null;

  @JsonProperty("dueDate")
  private String dueDate = null;

  @JsonProperty("retentionDate")
  private OffsetDateTime retentionDate = null;

  @JsonProperty("lastPayment")
  private Boolean lastPayment = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("officeName")
  private String officeName = null;

  @JsonProperty("debtor")
  private CtSubjectTypePafn debtor = null;

  @JsonProperty("transferList")
  private CtTransferListPATypePafn transferList = null;

  @JsonProperty("metadata")
  private CtMetadataTypePafn metadata = null;

  public CtPaymentPATypePafn creditorReferenceId(String creditorReferenceId) {
    this.creditorReferenceId = creditorReferenceId;
    return this;
  }

  /**
   * Get creditorReferenceId
   * 
   * @return creditorReferenceId
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getCreditorReferenceId() {
    return creditorReferenceId;
  }

  public void setCreditorReferenceId(String creditorReferenceId) {
    this.creditorReferenceId = creditorReferenceId;
  }

  public CtPaymentPATypePafn paymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
    return this;
  }

  /**
   * Get paymentAmount
   * 
   * @return paymentAmount
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  @Pattern(regexp = "\\d+\\.\\d{2}")
  public BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public CtPaymentPATypePafn dueDate(String dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * Get dueDate
   * 
   * @return dueDate
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public CtPaymentPATypePafn retentionDate(OffsetDateTime retentionDate) {
    this.retentionDate = retentionDate;
    return this;
  }

  /**
   * Get retentionDate
   * 
   * @return retentionDate
   **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getRetentionDate() {
    return retentionDate;
  }

  public void setRetentionDate(OffsetDateTime retentionDate) {
    this.retentionDate = retentionDate;
  }

  public CtPaymentPATypePafn lastPayment(Boolean lastPayment) {
    this.lastPayment = lastPayment;
    return this;
  }

  /**
   * Get lastPayment
   * 
   * @return lastPayment
   **/
  @ApiModelProperty(value = "")

  public Boolean isLastPayment() {
    return lastPayment;
  }

  public void setLastPayment(Boolean lastPayment) {
    this.lastPayment = lastPayment;
  }

  public CtPaymentPATypePafn description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * 
   * @return description
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 140)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CtPaymentPATypePafn companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  /**
   * Get companyName
   * 
   * @return companyName
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 140)
  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public CtPaymentPATypePafn officeName(String officeName) {
    this.officeName = officeName;
    return this;
  }

  /**
   * Get officeName
   * 
   * @return officeName
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 140)
  public String getOfficeName() {
    return officeName;
  }

  public void setOfficeName(String officeName) {
    this.officeName = officeName;
  }

  public CtPaymentPATypePafn debtor(CtSubjectTypePafn debtor) {
    this.debtor = debtor;
    return this;
  }

  /**
   * Get debtor
   * 
   * @return debtor
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CtSubjectTypePafn getDebtor() {
    return debtor;
  }

  public void setDebtor(CtSubjectTypePafn debtor) {
    this.debtor = debtor;
  }

  public CtPaymentPATypePafn transferList(CtTransferListPATypePafn transferList) {
    this.transferList = transferList;
    return this;
  }

  /**
   * Get transferList
   * 
   * @return transferList
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CtTransferListPATypePafn getTransferList() {
    return transferList;
  }

  public void setTransferList(CtTransferListPATypePafn transferList) {
    this.transferList = transferList;
  }

  public CtPaymentPATypePafn metadata(CtMetadataTypePafn metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Get metadata
   * 
   * @return metadata
   **/
  @ApiModelProperty(value = "")

  @Valid

  public CtMetadataTypePafn getMetadata() {
    return metadata;
  }

  public void setMetadata(CtMetadataTypePafn metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtPaymentPATypePafn ctPaymentPATypePafn = (CtPaymentPATypePafn) o;
    return Objects.equals(this.creditorReferenceId, ctPaymentPATypePafn.creditorReferenceId)
        && Objects.equals(this.paymentAmount, ctPaymentPATypePafn.paymentAmount)
        && Objects.equals(this.dueDate, ctPaymentPATypePafn.dueDate)
        && Objects.equals(this.retentionDate, ctPaymentPATypePafn.retentionDate)
        && Objects.equals(this.lastPayment, ctPaymentPATypePafn.lastPayment)
        && Objects.equals(this.description, ctPaymentPATypePafn.description)
        && Objects.equals(this.companyName, ctPaymentPATypePafn.companyName)
        && Objects.equals(this.officeName, ctPaymentPATypePafn.officeName)
        && Objects.equals(this.debtor, ctPaymentPATypePafn.debtor)
        && Objects.equals(this.transferList, ctPaymentPATypePafn.transferList)
        && Objects.equals(this.metadata, ctPaymentPATypePafn.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creditorReferenceId, paymentAmount, dueDate, retentionDate, lastPayment, description,
        companyName, officeName, debtor, transferList, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtPaymentPATypePafn {\n");

    sb.append("    creditorReferenceId: ").append(toIndentedString(creditorReferenceId)).append("\n");
    sb.append("    paymentAmount: ").append(toIndentedString(paymentAmount)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    retentionDate: ").append(toIndentedString(retentionDate)).append("\n");
    sb.append("    lastPayment: ").append(toIndentedString(lastPayment)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    officeName: ").append(toIndentedString(officeName)).append("\n");
    sb.append("    debtor: ").append(toIndentedString(debtor)).append("\n");
    sb.append("    transferList: ").append(toIndentedString(transferList)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
