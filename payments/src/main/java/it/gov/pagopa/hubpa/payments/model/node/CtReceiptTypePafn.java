package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Its contains all receipt information : **identifier section** -
 * &#x60;outcome&#x60; : result of receipt **OK** / **KO** -
 * &#x60;receiptId&#x60; : unique identifier of receipt (assigned by pagoPa) it
 * contains &#x60;paymentToken&#x60; present in to
 * &#x60;activatePaymentNotice&#x60; response - &#x60;noticeNumber&#x60; :
 * notice number - &#x60;fiscalCode&#x60; : Tax code of the public
 * administration **PA data** - &#x60;creditorReferenceId&#x60; : **IUV**
 * _Identificativo Univoco Versamento_ - &#x60;paymentAmount&#x60; : amount -
 * &#x60;description&#x60; : - &#x60;companyName&#x60; : Public Administration
 * full name - &#x60;officeName&#x60; Public Administration Department Name -
 * &#x60;debtor&#x60; : debtor subject identifier - &#x60;transferList&#x60; :
 * the list of transfers - &#x60;metadata&#x60; : info received in to
 * &#x60;paGetPaymentRes&#x60; **PSP data** - &#x60;idPSP&#x60; : PSP
 * Identifier, it has been assigned from pagoPA. - &#x60;pspFiscalCode&#x60; :
 * PSP&#39; fiscal code - &#x60;pspPartitaIVA&#x60; : PSP&#39; _Partita IVA_ -
 * &#x60;PSPCompanyName&#x60; : PSP full name - &#x60;idChannel&#x60; : Channel
 * Identifier, it identifies a payment service category and through which the
 * transaction is carried out. - &#x60;channelDescription&#x60; : Channel
 * Identifier description - &#x60;payer&#x60; : who made the payment -
 * &#x60;paymentMethod&#x60; : Method of the payment , i.e. &#x60;cash&#x60;,
 * &#x60;creditCard&#x60;, &#x60;bancomat&#x60; or &#x60;other&#x60; -
 * &#x60;fee&#x60; : PSP&#39;s fee applied - &#x60;paymentDateTime&#x60; :
 * payment execution date by the user - &#x60;applicationDate&#x60; :
 * application date, payment date on the PSP side - &#x60;transferDate&#x60; :
 * transfer date
 */
@ApiModel(description = "Its contains all receipt information :  **identifier section** - `outcome` : result of receipt **OK** / **KO** - `receiptId` : unique identifier of receipt (assigned by pagoPa) it contains `paymentToken` present in to `activatePaymentNotice` response  - `noticeNumber` : notice number - `fiscalCode` : Tax code of the public administration  **PA data** - `creditorReferenceId` : **IUV** _Identificativo Univoco Versamento_ - `paymentAmount` : amount - `description` :  - `companyName` : Public Administration full name - `officeName` Public Administration Department Name - `debtor` : debtor subject identifier - `transferList` : the list of transfers - `metadata` : info received in to `paGetPaymentRes`  **PSP data** - `idPSP` : PSP Identifier, it has been assigned from pagoPA. - `pspFiscalCode` : PSP' fiscal code - `pspPartitaIVA` : PSP' _Partita IVA_ - `PSPCompanyName` : PSP full name - `idChannel` : Channel Identifier, it identifies a payment service category and through which the transaction is carried out. - `channelDescription` : Channel Identifier description - `payer` : who made the payment - `paymentMethod` : Method of the payment , i.e. `cash`, `creditCard`, `bancomat` or `other` - `fee` : PSP's fee applied - `paymentDateTime` : payment execution date by the user - `applicationDate` : application date, payment date on the PSP side - `transferDate` : transfer date")
@Validated
public class CtReceiptTypePafn {
  @JsonProperty("receiptId")
  private String receiptId = null;

  @JsonProperty("noticeNumber")
  private String noticeNumber = null;

  @JsonProperty("fiscalCode")
  private String fiscalCode = null;

  @JsonProperty("outcome")
  private StOutcomeTypePafn outcome = null;

  @JsonProperty("creditorReferenceId")
  private String creditorReferenceId = null;

  @JsonProperty("paymentAmount")
  private BigDecimal paymentAmount = null;

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

  @JsonProperty("idPSP")
  private String idPSP = null;

  @JsonProperty("pspFiscalCode")
  private String pspFiscalCode = null;

  @JsonProperty("pspPartitaIVA")
  private String pspPartitaIVA = null;

  @JsonProperty("PSPCompanyName")
  private String psPCompanyName = null;

  @JsonProperty("idChannel")
  private String idChannel = null;

  @JsonProperty("channelDescription")
  private String channelDescription = null;

  @JsonProperty("payer")
  private CtSubjectTypePafn payer = null;

  @JsonProperty("paymentMethod")
  private String paymentMethod = null;

  @JsonProperty("fee")
  private BigDecimal fee = null;

  @JsonProperty("paymentDateTime")
  private String paymentDateTime = null;

  @JsonProperty("applicationDate")
  private String applicationDate = null;

  @JsonProperty("transferDate")
  private String transferDate = null;

  @JsonProperty("metadata")
  private CtMetadataTypePafn metadata = null;

  public CtReceiptTypePafn receiptId(String receiptId) {
    this.receiptId = receiptId;
    return this;
  }

  /**
   * Get receiptId
   * 
   * @return receiptId
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  public String getReceiptId() {
    return receiptId;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public CtReceiptTypePafn noticeNumber(String noticeNumber) {
    this.noticeNumber = noticeNumber;
    return this;
  }

  /**
   * Get noticeNumber
   * 
   * @return noticeNumber
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Pattern(regexp = "[0-9]{18}")
  public String getNoticeNumber() {
    return noticeNumber;
  }

  public void setNoticeNumber(String noticeNumber) {
    this.noticeNumber = noticeNumber;
  }

  public CtReceiptTypePafn fiscalCode(String fiscalCode) {
    this.fiscalCode = fiscalCode;
    return this;
  }

  /**
   * Get fiscalCode
   * 
   * @return fiscalCode
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Pattern(regexp = "[0-9]{11}")
  @Size(min = 11, max = 11)
  public String getFiscalCode() {
    return fiscalCode;
  }

  public void setFiscalCode(String fiscalCode) {
    this.fiscalCode = fiscalCode;
  }

  public CtReceiptTypePafn outcome(StOutcomeTypePafn outcome) {
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

  public CtReceiptTypePafn creditorReferenceId(String creditorReferenceId) {
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

  public CtReceiptTypePafn paymentAmount(BigDecimal paymentAmount) {
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
@Pattern(regexp="\\d+\\.\\d{2}") 
  public BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public CtReceiptTypePafn description(String description) {
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

  public CtReceiptTypePafn companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

  /**
   * Get companyName
   * 
   * @return companyName
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 140)
  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public CtReceiptTypePafn officeName(String officeName) {
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

  public CtReceiptTypePafn debtor(CtSubjectTypePafn debtor) {
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

  public CtReceiptTypePafn transferList(CtTransferListPATypePafn transferList) {
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

  public CtReceiptTypePafn idPSP(String idPSP) {
    this.idPSP = idPSP;
    return this;
  }

  /**
   * Get idPSP
   * 
   * @return idPSP
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getIdPSP() {
    return idPSP;
  }

  public void setIdPSP(String idPSP) {
    this.idPSP = idPSP;
  }

  public CtReceiptTypePafn pspFiscalCode(String pspFiscalCode) {
    this.pspFiscalCode = pspFiscalCode;
    return this;
  }

  /**
   * Get pspFiscalCode
   * 
   * @return pspFiscalCode
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 70)
  public String getPspFiscalCode() {
    return pspFiscalCode;
  }

  public void setPspFiscalCode(String pspFiscalCode) {
    this.pspFiscalCode = pspFiscalCode;
  }

  public CtReceiptTypePafn pspPartitaIVA(String pspPartitaIVA) {
    this.pspPartitaIVA = pspPartitaIVA;
    return this;
  }

  /**
   * Get pspPartitaIVA
   * 
   * @return pspPartitaIVA
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 20)
  public String getPspPartitaIVA() {
    return pspPartitaIVA;
  }

  public void setPspPartitaIVA(String pspPartitaIVA) {
    this.pspPartitaIVA = pspPartitaIVA;
  }

  public CtReceiptTypePafn psPCompanyName(String psPCompanyName) {
    this.psPCompanyName = psPCompanyName;
    return this;
  }

  /**
   * Get psPCompanyName
   * 
   * @return psPCompanyName
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getPsPCompanyName() {
    return psPCompanyName;
  }

  public void setPsPCompanyName(String psPCompanyName) {
    this.psPCompanyName = psPCompanyName;
  }

  public CtReceiptTypePafn idChannel(String idChannel) {
    this.idChannel = idChannel;
    return this;
  }

  /**
   * Get idChannel
   * 
   * @return idChannel
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getIdChannel() {
    return idChannel;
  }

  public void setIdChannel(String idChannel) {
    this.idChannel = idChannel;
  }

  public CtReceiptTypePafn channelDescription(String channelDescription) {
    this.channelDescription = channelDescription;
    return this;
  }

  /**
   * Get channelDescription
   * 
   * @return channelDescription
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getChannelDescription() {
    return channelDescription;
  }

  public void setChannelDescription(String channelDescription) {
    this.channelDescription = channelDescription;
  }

  public CtReceiptTypePafn payer(CtSubjectTypePafn payer) {
    this.payer = payer;
    return this;
  }

  /**
   * Get payer
   * 
   * @return payer
   **/
  @ApiModelProperty(value = "")

  @Valid

  public CtSubjectTypePafn getPayer() {
    return payer;
  }

  public void setPayer(CtSubjectTypePafn payer) {
    this.payer = payer;
  }

  public CtReceiptTypePafn paymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
    return this;
  }

  /**
   * Get paymentMethod
   * 
   * @return paymentMethod
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 35)
  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public CtReceiptTypePafn fee(BigDecimal fee) {
    this.fee = fee;
    return this;
  }

  /**
   * Get fee
   * 
   * @return fee
   **/
  @ApiModelProperty(value = "")

  @Valid
  @Pattern
  (regexp="\\d+\\.\\d{2}")public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public CtReceiptTypePafn paymentDateTime(String paymentDateTime) {
    this.paymentDateTime = paymentDateTime;
    return this;
  }

  /**
   * Get paymentDateTime
   * 
   * @return paymentDateTime
   **/
  @ApiModelProperty(value = "")

  public String getPaymentDateTime() {
    return paymentDateTime;
  }

  public void setPaymentDateTime(String paymentDateTime) {
    this.paymentDateTime = paymentDateTime;
  }

  public CtReceiptTypePafn applicationDate(String applicationDate) {
    this.applicationDate = applicationDate;
    return this;
  }

  /**
   * Get applicationDate
   * 
   * @return applicationDate
   **/
  @ApiModelProperty(value = "")

  public String getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(String applicationDate) {
    this.applicationDate = applicationDate;
  }

  public CtReceiptTypePafn transferDate(String transferDate) {
    this.transferDate = transferDate;
    return this;
  }

  /**
   * Get transferDate
   * 
   * @return transferDate
   **/
  @ApiModelProperty(value = "")

  public String getTransferDate() {
    return transferDate;
  }

  public void setTransferDate(String transferDate) {
    this.transferDate = transferDate;
  }

  public CtReceiptTypePafn metadata(CtMetadataTypePafn metadata) {
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
    CtReceiptTypePafn ctReceiptTypePafn = (CtReceiptTypePafn) o;
    return Objects.equals(this.receiptId, ctReceiptTypePafn.receiptId)
        && Objects.equals(this.noticeNumber, ctReceiptTypePafn.noticeNumber)
        && Objects.equals(this.fiscalCode, ctReceiptTypePafn.fiscalCode)
        && Objects.equals(this.outcome, ctReceiptTypePafn.outcome)
        && Objects.equals(this.creditorReferenceId, ctReceiptTypePafn.creditorReferenceId)
        && Objects.equals(this.paymentAmount, ctReceiptTypePafn.paymentAmount)
        && Objects.equals(this.description, ctReceiptTypePafn.description)
        && Objects.equals(this.companyName, ctReceiptTypePafn.companyName)
        && Objects.equals(this.officeName, ctReceiptTypePafn.officeName)
        && Objects.equals(this.debtor, ctReceiptTypePafn.debtor)
        && Objects.equals(this.transferList, ctReceiptTypePafn.transferList)
        && Objects.equals(this.idPSP, ctReceiptTypePafn.idPSP)
        && Objects.equals(this.pspFiscalCode, ctReceiptTypePafn.pspFiscalCode)
        && Objects.equals(this.pspPartitaIVA, ctReceiptTypePafn.pspPartitaIVA)
        && Objects.equals(this.psPCompanyName, ctReceiptTypePafn.psPCompanyName)
        && Objects.equals(this.idChannel, ctReceiptTypePafn.idChannel)
        && Objects.equals(this.channelDescription, ctReceiptTypePafn.channelDescription)
        && Objects.equals(this.payer, ctReceiptTypePafn.payer)
        && Objects.equals(this.paymentMethod, ctReceiptTypePafn.paymentMethod)
        && Objects.equals(this.fee, ctReceiptTypePafn.fee)
        && Objects.equals(this.paymentDateTime, ctReceiptTypePafn.paymentDateTime)
        && Objects.equals(this.applicationDate, ctReceiptTypePafn.applicationDate)
        && Objects.equals(this.transferDate, ctReceiptTypePafn.transferDate)
        && Objects.equals(this.metadata, ctReceiptTypePafn.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(receiptId, noticeNumber, fiscalCode, outcome, creditorReferenceId, paymentAmount, description,
        companyName, officeName, debtor, transferList, idPSP, pspFiscalCode, pspPartitaIVA, psPCompanyName, idChannel,
        channelDescription, payer, paymentMethod, fee, paymentDateTime, applicationDate, transferDate, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtReceiptTypePafn {\n");

    sb.append("    receiptId: ").append(toIndentedString(receiptId)).append("\n");
    sb.append("    noticeNumber: ").append(toIndentedString(noticeNumber)).append("\n");
    sb.append("    fiscalCode: ").append(toIndentedString(fiscalCode)).append("\n");
    sb.append("    outcome: ").append(toIndentedString(outcome)).append("\n");
    sb.append("    creditorReferenceId: ").append(toIndentedString(creditorReferenceId)).append("\n");
    sb.append("    paymentAmount: ").append(toIndentedString(paymentAmount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    officeName: ").append(toIndentedString(officeName)).append("\n");
    sb.append("    debtor: ").append(toIndentedString(debtor)).append("\n");
    sb.append("    transferList: ").append(toIndentedString(transferList)).append("\n");
    sb.append("    idPSP: ").append(toIndentedString(idPSP)).append("\n");
    sb.append("    pspFiscalCode: ").append(toIndentedString(pspFiscalCode)).append("\n");
    sb.append("    pspPartitaIVA: ").append(toIndentedString(pspPartitaIVA)).append("\n");
    sb.append("    psPCompanyName: ").append(toIndentedString(psPCompanyName)).append("\n");
    sb.append("    idChannel: ").append(toIndentedString(idChannel)).append("\n");
    sb.append("    channelDescription: ").append(toIndentedString(channelDescription)).append("\n");
    sb.append("    payer: ").append(toIndentedString(payer)).append("\n");
    sb.append("    paymentMethod: ").append(toIndentedString(paymentMethod)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    paymentDateTime: ").append(toIndentedString(paymentDateTime)).append("\n");
    sb.append("    applicationDate: ").append(toIndentedString(applicationDate)).append("\n");
    sb.append("    transferDate: ").append(toIndentedString(transferDate)).append("\n");
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
