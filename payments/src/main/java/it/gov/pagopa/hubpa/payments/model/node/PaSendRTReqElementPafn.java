package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The &#x60;paSendRT&#x60; request contains : - &#x60;idPA&#x60; : alphanumeric
 * field containing the tax code of the structure sending the payment request. -
 * &#x60;idBrokerPA&#x60; : identification of subject that operates as an
 * intermediary for the PA. - &#x60;idStation&#x60; : identification of the
 * station of the PA into pagoPa system. - &#x60;receipt&#x60; : the payment
 * receipt (_see below to details_)
 */
@ApiModel(description = "The `paSendRT` request contains : - `idPA` : alphanumeric field containing the tax code of the structure sending the payment request. - `idBrokerPA` : identification of subject that operates as an intermediary for the PA. - `idStation` : identification of the station of the PA into pagoPa system. - `receipt` : the payment receipt (_see below to details_)")
@Validated
public class PaSendRTReqElementPafn {
  @JsonProperty("idPA")
  private String idPA = null;

  @JsonProperty("idBrokerPA")
  private String idBrokerPA = null;

  @JsonProperty("idStation")
  private String idStation = null;

  @JsonProperty("receipt")
  private CtReceiptTypePafn receipt = null;

  public PaSendRTReqElementPafn idPA(String idPA) {
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

  public PaSendRTReqElementPafn idBrokerPA(String idBrokerPA) {
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

  public PaSendRTReqElementPafn idStation(String idStation) {
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

  public PaSendRTReqElementPafn receipt(CtReceiptTypePafn receipt) {
    this.receipt = receipt;
    return this;
  }

  /**
   * Get receipt
   * 
   * @return receipt
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CtReceiptTypePafn getReceipt() {
    return receipt;
  }

  public void setReceipt(CtReceiptTypePafn receipt) {
    this.receipt = receipt;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaSendRTReqElementPafn paSendRTReqElementPafn = (PaSendRTReqElementPafn) o;
    return Objects.equals(this.idPA, paSendRTReqElementPafn.idPA)
        && Objects.equals(this.idBrokerPA, paSendRTReqElementPafn.idBrokerPA)
        && Objects.equals(this.idStation, paSendRTReqElementPafn.idStation)
        && Objects.equals(this.receipt, paSendRTReqElementPafn.receipt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idPA, idBrokerPA, idStation, receipt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaSendRTReqElementPafn {\n");

    sb.append("    idPA: ").append(toIndentedString(idPA)).append("\n");
    sb.append("    idBrokerPA: ").append(toIndentedString(idBrokerPA)).append("\n");
    sb.append("    idStation: ").append(toIndentedString(idStation)).append("\n");
    sb.append("    receipt: ").append(toIndentedString(receipt)).append("\n");
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
