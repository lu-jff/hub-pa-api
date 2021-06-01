package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Contains all data for the subject of payment : - &#x60;uniqueIdentifier&#x60;
 * : (_see below to details_) - &#x60;fullName&#x60; : name of the subject -
 * &#x60;streetName&#x60; : street name - &#x60;civicNumber&#x60; : building
 * number - &#x60;postalCode&#x60; : postal code - &#x60;city&#x60; : town name
 * - &#x60;stateProvinceRegion&#x60; : country subdivision - &#x60;country&#x60;
 * : country name - &#x60;e-mail&#x60; : remittance location electronic address
 */
@ApiModel(description = "Contains all data for the subject of payment :  - `uniqueIdentifier` : (_see below to details_) - `fullName` : name of the subject - `streetName` : street name - `civicNumber` : building number - `postalCode` : postal code - `city` : town name - `stateProvinceRegion` : country subdivision - `country` : country name - `e-mail` : remittance location electronic address")
@Validated
public class CtSubjectTypePafn {
  @JsonProperty("uniqueIdentifier")
  private CtEntityUniqueIdentifierTypePafn uniqueIdentifier = null;

  @JsonProperty("fullName")
  private String fullName = null;

  @JsonProperty("streetName")
  private String streetName = null;

  @JsonProperty("civicNumber")
  private String civicNumber = null;

  @JsonProperty("postalCode")
  private String postalCode = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("stateProvinceRegion")
  private String stateProvinceRegion = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("e-mail")
  private String eMail = null;

  public CtSubjectTypePafn uniqueIdentifier(CtEntityUniqueIdentifierTypePafn uniqueIdentifier) {
    this.uniqueIdentifier = uniqueIdentifier;
    return this;
  }

  /**
   * Get uniqueIdentifier
   * 
   * @return uniqueIdentifier
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public CtEntityUniqueIdentifierTypePafn getUniqueIdentifier() {
    return uniqueIdentifier;
  }

  public void setUniqueIdentifier(CtEntityUniqueIdentifierTypePafn uniqueIdentifier) {
    this.uniqueIdentifier = uniqueIdentifier;
  }

  public CtSubjectTypePafn fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * 
   * @return fullName
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 70)
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public CtSubjectTypePafn streetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  /**
   * Get streetName
   * 
   * @return streetName
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 70)
  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public CtSubjectTypePafn civicNumber(String civicNumber) {
    this.civicNumber = civicNumber;
    return this;
  }

  /**
   * Get civicNumber
   * 
   * @return civicNumber
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 16)
  public String getCivicNumber() {
    return civicNumber;
  }

  public void setCivicNumber(String civicNumber) {
    this.civicNumber = civicNumber;
  }

  public CtSubjectTypePafn postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * 
   * @return postalCode
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 16)
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public CtSubjectTypePafn city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * 
   * @return city
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 35)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public CtSubjectTypePafn stateProvinceRegion(String stateProvinceRegion) {
    this.stateProvinceRegion = stateProvinceRegion;
    return this;
  }

  /**
   * Get stateProvinceRegion
   * 
   * @return stateProvinceRegion
   **/
  @ApiModelProperty(value = "")

  @Size(min = 1, max = 35)
  public String getStateProvinceRegion() {
    return stateProvinceRegion;
  }

  public void setStateProvinceRegion(String stateProvinceRegion) {
    this.stateProvinceRegion = stateProvinceRegion;
  }

  public CtSubjectTypePafn country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * 
   * @return country
   **/
  @ApiModelProperty(value = "")

  @Pattern(regexp = "[A-Z]{2,2}")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public CtSubjectTypePafn eMail(String eMail) {
    this.eMail = eMail;
    return this;
  }

  /**
   * Get eMail
   * 
   * @return eMail
   **/
  @ApiModelProperty(value = "")

  @Pattern(regexp = "[a-zA-Z0-9_\\.\\+\\-]+@[a-zA-Z0-9\\-]+(\\.[a-zA-Z0-9\\-]+)*")
  @Size(max = 256)
  public String getEMail() {
    return eMail;
  }

  public void setEMail(String eMail) {
    this.eMail = eMail;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtSubjectTypePafn ctSubjectTypePafn = (CtSubjectTypePafn) o;
    return Objects.equals(this.uniqueIdentifier, ctSubjectTypePafn.uniqueIdentifier)
        && Objects.equals(this.fullName, ctSubjectTypePafn.fullName)
        && Objects.equals(this.streetName, ctSubjectTypePafn.streetName)
        && Objects.equals(this.civicNumber, ctSubjectTypePafn.civicNumber)
        && Objects.equals(this.postalCode, ctSubjectTypePafn.postalCode)
        && Objects.equals(this.city, ctSubjectTypePafn.city)
        && Objects.equals(this.stateProvinceRegion, ctSubjectTypePafn.stateProvinceRegion)
        && Objects.equals(this.country, ctSubjectTypePafn.country)
        && Objects.equals(this.eMail, ctSubjectTypePafn.eMail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uniqueIdentifier, fullName, streetName, civicNumber, postalCode, city, stateProvinceRegion,
        country, eMail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtSubjectTypePafn {\n");

    sb.append("    uniqueIdentifier: ").append(toIndentedString(uniqueIdentifier)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    civicNumber: ").append(toIndentedString(civicNumber)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    stateProvinceRegion: ").append(toIndentedString(stateProvinceRegion)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    eMail: ").append(toIndentedString(eMail)).append("\n");
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
