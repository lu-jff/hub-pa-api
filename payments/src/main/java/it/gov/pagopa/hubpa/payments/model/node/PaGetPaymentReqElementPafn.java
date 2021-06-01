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
 * The &#x60;paVerifyPaymentNotice&#x60; request contains : - &#x60;idPA&#x60; :
 * alphanumeric field containing the tax code of the structure sending the
 * payment request. - &#x60;idBrokerPA&#x60; : identification of subject that
 * operates as an intermediary for the PA. - &#x60;idStation&#x60; :
 * identification of the station of the PA into pagoPa system. -
 * &#x60;qrCode&#x60; : is the union of &#x60;fiscalCode&#x60; and
 * &#x60;noticeNumber&#x60; - &#x60;amount&#x60; : amount of the payment -
 * &#x60;paymentNote&#x60; : details description of the payment -
 * &#x60;transferType&#x60; : _specific only for POSTE Italiane_ -
 * &#x60;dueDate&#x60; : indicates the expiration payment date according to the
 * ISO 8601 format &#x60;[YYYY]-[MM]-[DD]&#x60;.
 */
@ApiModel(description = "The `paVerifyPaymentNotice` request contains : - `idPA` : alphanumeric field containing the tax code of the structure sending the payment request. - `idBrokerPA` : identification of subject that operates as an intermediary for the PA. - `idStation` : identification of the station of the PA into pagoPa system. - `qrCode` : is the union of `fiscalCode` and `noticeNumber` - `amount` : amount of the payment - `paymentNote` : details description of the payment - `transferType` : _specific only for POSTE Italiane_ - `dueDate` : indicates the expiration payment date according to the ISO 8601 format `[YYYY]-[MM]-[DD]`.")
@Validated
public class PaGetPaymentReqElementPafn {
  @JsonProperty("idPA")
  private String idPA = null;

  @JsonProperty("idBrokerPA")
  private String idBrokerPA = null;

  @JsonProperty("idStation")
  private String idStation = null;

  @JsonProperty("qrCode")
  private CtQrCodeTypePafn qrCode = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("paymentNote")
  private String paymentNote = null;

  @JsonProperty("transferType")
  private StTransferTypeTypePafn transferType = null;

  @JsonProperty("dueDate")
  private String dueDate = null;

  public PaGetPaymentReqElementPafn idPA(String idPA) {
    this.idPA = idPA;
    return this;
  }

  /**
   * Get idPA
   * 
   * @return idPA
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getIdPA() {
    return idPA;
  }

  public void setIdPA(String idPA) {
    this.idPA = idPA;
  }

  public PaGetPaymentReqElementPafn idBrokerPA(String idBrokerPA) {
    this.idBrokerPA = idBrokerPA;
    return this;
  }

  /**
   * Get idBrokerPA
   * 
   * @return idBrokerPA
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getIdBrokerPA() {
    return idBrokerPA;
  }

  public void setIdBrokerPA(String idBrokerPA) {
    this.idBrokerPA = idBrokerPA;
  }

  public PaGetPaymentReqElementPafn idStation(String idStation) {
    this.idStation = idStation;
    return this;
  }

  /**
   * Get idStation
   * 
   * @return idStation
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getIdStation() {
    return idStation;
  }

  public void setIdStation(String idStation) {
    this.idStation = idStation;
  }

  public PaGetPaymentReqElementPafn qrCode(CtQrCodeTypePafn qrCode) {
    this.qrCode = qrCode;
    return this;
  }

  /**
   * Get qrCode
   * 
   * @return qrCode
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CtQrCodeTypePafn getQrCode() {
    return qrCode;
  }

  public void setQrCode(CtQrCodeTypePafn qrCode) {
    this.qrCode = qrCode;
  }

  public PaGetPaymentReqElementPafn amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * 
   * @return amount
   **/
  @ApiModelProperty(value = "")

  @Valid
@Pattern(regexp="\\d+\\.\\d{2}") 
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public PaGetPaymentReqElementPafn paymentNote(String paymentNote) {
    this.paymentNote = paymentNote;
    return this;
  }

  /**
   * Get paymentNote
   * 
   * @return paymentNote
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 210)
  public String getPaymentNote() {
    return paymentNote;
  }

  public void setPaymentNote(String paymentNote) {
    this.paymentNote = paymentNote;
  }

  public PaGetPaymentReqElementPafn transferType(StTransferTypeTypePafn transferType) {
    this.transferType = transferType;
    return this;
  }

  /**
   * Get transferType
   * 
   * @return transferType
   **/
  @ApiModelProperty(value = "")

  @Valid

  public StTransferTypeTypePafn getTransferType() {
    return transferType;
  }

  public void setTransferType(StTransferTypeTypePafn transferType) {
    this.transferType = transferType;
  }

  public PaGetPaymentReqElementPafn dueDate(String dueDate) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaGetPaymentReqElementPafn paGetPaymentReqElementPafn = (PaGetPaymentReqElementPafn) o;
    return Objects.equals(this.idPA, paGetPaymentReqElementPafn.idPA)
        && Objects.equals(this.idBrokerPA, paGetPaymentReqElementPafn.idBrokerPA)
        && Objects.equals(this.idStation, paGetPaymentReqElementPafn.idStation)
        && Objects.equals(this.qrCode, paGetPaymentReqElementPafn.qrCode)
        && Objects.equals(this.amount, paGetPaymentReqElementPafn.amount)
        && Objects.equals(this.paymentNote, paGetPaymentReqElementPafn.paymentNote)
        && Objects.equals(this.transferType, paGetPaymentReqElementPafn.transferType)
        && Objects.equals(this.dueDate, paGetPaymentReqElementPafn.dueDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPA, idBrokerPA, idStation, qrCode, amount, paymentNote, transferType, dueDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaGetPaymentReqElementPafn {\n");

    sb.append("    idPA: ").append(toIndentedString(idPA)).append("\n");
    sb.append("    idBrokerPA: ").append(toIndentedString(idBrokerPA)).append("\n");
    sb.append("    idStation: ").append(toIndentedString(idStation)).append("\n");
    sb.append("    qrCode: ").append(toIndentedString(qrCode)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    paymentNote: ").append(toIndentedString(paymentNote)).append("\n");
    sb.append("    transferType: ").append(toIndentedString(transferType)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
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
