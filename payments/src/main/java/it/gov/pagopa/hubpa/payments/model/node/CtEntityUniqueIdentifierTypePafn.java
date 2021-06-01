package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CtEntityUniqueIdentifierTypePafn
 */
@Validated
public class CtEntityUniqueIdentifierTypePafn {

  @JsonProperty("entityUniqueIdentifierType")
  private StEntityUniqueIdentifierTypeTypePafn entityUniqueIdentifierType = null;

  @JsonProperty("entityUniqueIdentifierValue")
  private String entityUniqueIdentifierValue = null;

  public CtEntityUniqueIdentifierTypePafn entityUniqueIdentifierType(
      StEntityUniqueIdentifierTypeTypePafn entityUniqueIdentifierType) {

    this.entityUniqueIdentifierType = entityUniqueIdentifierType;
    return this;
  }

  /**
   * Get entityUniqueIdentifierType
   * 
   * @return entityUniqueIdentifierType
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Valid
  @Size(min = 1, max = 1)
  public StEntityUniqueIdentifierTypeTypePafn getEntityUniqueIdentifierType() {

    return entityUniqueIdentifierType;
  }

  public void setEntityUniqueIdentifierType(StEntityUniqueIdentifierTypeTypePafn entityUniqueIdentifierType) {

    this.entityUniqueIdentifierType = entityUniqueIdentifierType;
  }

  public CtEntityUniqueIdentifierTypePafn entityUniqueIdentifierValue(String entityUniqueIdentifierValue) {

    this.entityUniqueIdentifierValue = entityUniqueIdentifierValue;
    return this;
  }

  /**
   * Get entityUniqueIdentifierValue
   * 
   * @return entityUniqueIdentifierValue
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Size(min = 2, max = 16)
  public String getEntityUniqueIdentifierValue() {

    return entityUniqueIdentifierValue;
  }

  public void setEntityUniqueIdentifierValue(String entityUniqueIdentifierValue) {

    this.entityUniqueIdentifierValue = entityUniqueIdentifierValue;
  }

  @Override
  public boolean equals(java.lang.Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtEntityUniqueIdentifierTypePafn ctEntityUniqueIdentifierTypePafn = (CtEntityUniqueIdentifierTypePafn) o;
    return Objects.equals(this.entityUniqueIdentifierType, ctEntityUniqueIdentifierTypePafn.entityUniqueIdentifierType)
        && Objects.equals(this.entityUniqueIdentifierValue,
            ctEntityUniqueIdentifierTypePafn.entityUniqueIdentifierValue);
  }

  @Override
  public int hashCode() {

    return Objects.hash(entityUniqueIdentifierType, entityUniqueIdentifierValue);
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("class CtEntityUniqueIdentifierTypePafn {\n");
    sb.append("    entityUniqueIdentifierType: ").append(toIndentedString(entityUniqueIdentifierType)).append("\n");
    sb.append("    entityUniqueIdentifierValue: ").append(toIndentedString(entityUniqueIdentifierValue)).append("\n");
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
