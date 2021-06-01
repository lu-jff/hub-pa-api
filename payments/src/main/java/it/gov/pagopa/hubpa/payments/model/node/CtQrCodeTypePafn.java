package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * Payment Notice **QR-CODE** data. QR-CODE is ISO/IEC 18004:2015 compliant
 * generated with: Parameters for generating the QR-CODE - Symbol Version : 4 -
 * Modules : 33x33 - Modules width : 3 pixels - ECC Level: M ( max correction
 * error 15%) - Character set : UTF-8 QR-CODE contains a string formatted as :
 * &#x60;PAGOPA|002|noticeNumber|fiscalCode|amount&#x60; Where
 * &#x60;noticeNumber&#x60; is composed by :
 * &#x60;[auxDigit][segregationCode][IUVBase][IUVCheckDigit]&#x60; While
 * &#x60;fiscalCode&#x60; is the creditor tax code.
 */
@ApiModel(description = "Payment Notice **QR-CODE** data. QR-CODE is ISO/IEC 18004:2015 compliant generated with:  Parameters for generating the QR-CODE  - Symbol Version : 4 - Modules : 33x33 - Modules width : 3 pixels - ECC Level: M ( max correction error 15%) - Character set : UTF-8  QR-CODE contains a string formatted as : `PAGOPA|002|noticeNumber|fiscalCode|amount`  Where `noticeNumber` is composed by :  `[auxDigit][segregationCode][IUVBase][IUVCheckDigit]`  While `fiscalCode` is the creditor tax code.")
@Validated
public class CtQrCodeTypePafn {
  @JsonProperty("fiscalCode")
  private String fiscalCode = null;

  @JsonProperty("noticeNumber")
  private String noticeNumber = null;

  public CtQrCodeTypePafn fiscalCode(String fiscalCode) {
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

  public CtQrCodeTypePafn noticeNumber(String noticeNumber) {
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

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtQrCodeTypePafn ctQrCodeTypePafn = (CtQrCodeTypePafn) o;
    return Objects.equals(this.fiscalCode, ctQrCodeTypePafn.fiscalCode)
        && Objects.equals(this.noticeNumber, ctQrCodeTypePafn.noticeNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fiscalCode, noticeNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtQrCodeTypePafn {\n");

    sb.append("    fiscalCode: ").append(toIndentedString(fiscalCode)).append("\n");
    sb.append("    noticeNumber: ").append(toIndentedString(noticeNumber)).append("\n");
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
