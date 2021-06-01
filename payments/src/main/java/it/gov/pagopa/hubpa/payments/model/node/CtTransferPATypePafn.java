package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CtTransferPATypePafn
 */
@Validated
public class CtTransferPATypePafn {
  @JsonProperty("idTransfer")
  private StIdTransferTypePafn idTransfer = null;

  @JsonProperty("transferAmount")
  private BigDecimal transferAmount = null;

  @JsonProperty("fiscalCodePA")
  private String fiscalCodePA = null;

  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("remittanceInformation")
  private String remittanceInformation = null;

  @JsonProperty("transferCategory")
  private String transferCategory = null;

  public CtTransferPATypePafn idTransfer(StIdTransferTypePafn idTransfer) {
    this.idTransfer = idTransfer;
    return this;
  }

  /**
   * Get idTransfer
   * 
   * @return idTransfer
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public StIdTransferTypePafn getIdTransfer() {
    return idTransfer;
  }

  public void setIdTransfer(StIdTransferTypePafn idTransfer) {
    this.idTransfer = idTransfer;
  }

  public CtTransferPATypePafn transferAmount(BigDecimal transferAmount) {
    this.transferAmount = transferAmount;
    return this;
  }

  /**
   * Get transferAmount
   * 
   * @return transferAmount
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  @Pattern(regexp = "\\d+\\.\\d{2}")
  public BigDecimal getTransferAmount() {
    return transferAmount;
  }

  public void setTransferAmount(BigDecimal transferAmount) {
    this.transferAmount = transferAmount;
  }

  public CtTransferPATypePafn fiscalCodePA(String fiscalCodePA) {
    this.fiscalCodePA = fiscalCodePA;
    return this;
  }

  /**
   * Get fiscalCodePA
   * 
   * @return fiscalCodePA
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Pattern(regexp = "[0-9]{11}")
  @Size(min = 11, max = 11)
  public String getFiscalCodePA() {
    return fiscalCodePA;
  }

  public void setFiscalCodePA(String fiscalCodePA) {
    this.fiscalCodePA = fiscalCodePA;
  }

  public CtTransferPATypePafn IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * 
   * @return IBAN
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 35)
  public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public CtTransferPATypePafn remittanceInformation(String remittanceInformation) {
    this.remittanceInformation = remittanceInformation;
    return this;
  }

  /**
   * Get remittanceInformation
   * 
   * @return remittanceInformation
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 140)
  public String getRemittanceInformation() {
    return remittanceInformation;
  }

  public void setRemittanceInformation(String remittanceInformation) {
    this.remittanceInformation = remittanceInformation;
  }

  public CtTransferPATypePafn transferCategory(String transferCategory) {
    this.transferCategory = transferCategory;
    return this;
  }

  /**
   * Get transferCategory
   * 
   * @return transferCategory
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 140)
  public String getTransferCategory() {
    return transferCategory;
  }

  public void setTransferCategory(String transferCategory) {
    this.transferCategory = transferCategory;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtTransferPATypePafn ctTransferPATypePafn = (CtTransferPATypePafn) o;
    return Objects.equals(this.idTransfer, ctTransferPATypePafn.idTransfer)
        && Objects.equals(this.transferAmount, ctTransferPATypePafn.transferAmount)
        && Objects.equals(this.fiscalCodePA, ctTransferPATypePafn.fiscalCodePA)
        && Objects.equals(this.IBAN, ctTransferPATypePafn.IBAN)
        && Objects.equals(this.remittanceInformation, ctTransferPATypePafn.remittanceInformation)
        && Objects.equals(this.transferCategory, ctTransferPATypePafn.transferCategory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idTransfer, transferAmount, fiscalCodePA, IBAN, remittanceInformation, transferCategory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtTransferPATypePafn {\n");

    sb.append("    idTransfer: ").append(toIndentedString(idTransfer)).append("\n");
    sb.append("    transferAmount: ").append(toIndentedString(transferAmount)).append("\n");
    sb.append("    fiscalCodePA: ").append(toIndentedString(fiscalCodePA)).append("\n");
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    remittanceInformation: ").append(toIndentedString(remittanceInformation)).append("\n");
    sb.append("    transferCategory: ").append(toIndentedString(transferCategory)).append("\n");
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
