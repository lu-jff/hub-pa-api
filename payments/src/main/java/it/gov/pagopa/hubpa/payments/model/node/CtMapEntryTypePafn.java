package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * CtMapEntryTypePafn
 */
@Validated
public class CtMapEntryTypePafn {
  @JsonProperty("key")
  private String key = null;

  @JsonProperty("value")
  private String value = null;

  public CtMapEntryTypePafn key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * 
   * @return key
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull
  @Size(min = 1, max = 140)
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public CtMapEntryTypePafn value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * 
   * @return value
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Size(min = 1, max = 140)
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtMapEntryTypePafn ctMapEntryTypePafn = (CtMapEntryTypePafn) o;
    return Objects.equals(this.key, ctMapEntryTypePafn.key) && Objects.equals(this.value, ctMapEntryTypePafn.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtMapEntryTypePafn {\n");

    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
