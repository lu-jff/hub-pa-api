package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Its a response to &#x60;paVerifyPaymentNoticeReq&#x60; and contains : -
 * &#x60;outcome&#x60; and _optional_ &#x60;fault&#x60; (_see below to details_)
 * - &#x60;paymentList&#x60; : the list of all available payment options (_see
 * below to details_) - &#x60;paymentDescription&#x60; : If the Public
 * Administration is configured as _OLD_ (i.e. still uses the old primitives)
 * this field must be set with the data &#x60;nodeTipoDatiPagamentoPA&#x60; of
 * the&#x60; nodeVerificaRPTRanspond&#x60; specifically: -
 * &#x60;causaleVersamento&#x60;: represents the extended description of the
 * reason for the payment, or - &#x60;spezzoniCausaleVersamento&#x60;: structure
 * available to Public Administration to specify the payment reasons. The size
 * of the current field is such as to allow the concatenation of the old
 * information previously described. - &#x60;fiscalCodePA&#x60; : Tax code of
 * the public administration - &#x60;companyName&#x60; : Public Administration
 * full name - &#x60;officeName&#x60; : Public Administration Department Name
 */
@ApiModel(description = "Its a response to `paVerifyPaymentNoticeReq` and contains :  - `outcome` and _optional_ `fault` (_see below to details_) - `paymentList` : the list of all available payment options (_see below to details_) - `paymentDescription` :   If the Public Administration is configured as _OLD_ (i.e. still uses the old primitives) this field must be set with the data `nodeTipoDatiPagamentoPA` of the` nodeVerificaRPTRanspond` specifically: - `causaleVersamento`: represents the extended description of the reason for the payment, or - `spezzoniCausaleVersamento`: structure available to Public Administration to specify the payment reasons.  The size of the current field is such as to allow the concatenation of the old information previously described.  - `fiscalCodePA` : Tax code of the public administration - `companyName` : Public Administration full name - `officeName` : Public Administration Department Name")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-01T12:59:52.852Z")

public class PaVerifyPaymentNoticeResElementPafn extends CtResponseTypePafn {
  @JsonProperty("paymentList")
  private CtPaymentOptionsDescriptionListPATypePafn paymentList = null;

  @JsonProperty("paymentDescription")
  private String paymentDescription = null;

  @JsonProperty("fiscalCodePA")
  private String fiscalCodePA = null;

  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("officeName")
  private String officeName = null;

  public PaVerifyPaymentNoticeResElementPafn paymentList(CtPaymentOptionsDescriptionListPATypePafn paymentList) {
    this.paymentList = paymentList;
    return this;
  }

  /**
   * Get paymentList
   * 
   * @return paymentList
   **/
  @ApiModelProperty(value = "")

  @Valid

  public CtPaymentOptionsDescriptionListPATypePafn getPaymentList() {
    return paymentList;
  }

  public void setPaymentList(CtPaymentOptionsDescriptionListPATypePafn paymentList) {
    this.paymentList = paymentList;
  }

  public PaVerifyPaymentNoticeResElementPafn paymentDescription(String paymentDescription) {
    this.paymentDescription = paymentDescription;
    return this;
  }

  /**
   * Get paymentDescription
   * 
   * @return paymentDescription
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 140)
  public String getPaymentDescription() {
    return paymentDescription;
  }

  public void setPaymentDescription(String paymentDescription) {
    this.paymentDescription = paymentDescription;
  }

  public PaVerifyPaymentNoticeResElementPafn fiscalCodePA(String fiscalCodePA) {
    this.fiscalCodePA = fiscalCodePA;
    return this;
  }

  /**
   * Get fiscalCodePA
   * 
   * @return fiscalCodePA
   **/
  @ApiModelProperty(value = "")

  @Pattern(regexp = "[0-9]{11}")
  @Size(min = 11, max = 11)
  public String getFiscalCodePA() {
    return fiscalCodePA;
  }

  public void setFiscalCodePA(String fiscalCodePA) {
    this.fiscalCodePA = fiscalCodePA;
  }

  public PaVerifyPaymentNoticeResElementPafn companyName(String companyName) {
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

  public PaVerifyPaymentNoticeResElementPafn officeName(String officeName) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaVerifyPaymentNoticeResElementPafn paVerifyPaymentNoticeResElementPafn = (PaVerifyPaymentNoticeResElementPafn) o;
    return Objects.equals(this.paymentList, paVerifyPaymentNoticeResElementPafn.paymentList)
        && Objects.equals(this.paymentDescription, paVerifyPaymentNoticeResElementPafn.paymentDescription)
        && Objects.equals(this.fiscalCodePA, paVerifyPaymentNoticeResElementPafn.fiscalCodePA)
        && Objects.equals(this.companyName, paVerifyPaymentNoticeResElementPafn.companyName)
        && Objects.equals(this.officeName, paVerifyPaymentNoticeResElementPafn.officeName) && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentList, paymentDescription, fiscalCodePA, companyName, officeName, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaVerifyPaymentNoticeResElementPafn {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    paymentList: ").append(toIndentedString(paymentList)).append("\n");
    sb.append("    paymentDescription: ").append(toIndentedString(paymentDescription)).append("\n");
    sb.append("    fiscalCodePA: ").append(toIndentedString(fiscalCodePA)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    officeName: ").append(toIndentedString(officeName)).append("\n");
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
