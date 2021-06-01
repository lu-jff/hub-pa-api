package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * Header for WS-Security
 */
@ApiModel(description = "Header for WS-Security")
@Validated
public class Security {
  @JsonProperty("UsernameToken")
  private Object usernameToken = null;

  @JsonProperty("Timestamp")
  private Object timestamp = null;

  public Security usernameToken(Object usernameToken) {
    this.usernameToken = usernameToken;
    return this;
  }

  /**
   * Get usernameToken
   * 
   * @return usernameToken
   **/
  @ApiModelProperty(value = "")

  public Object getUsernameToken() {
    return usernameToken;
  }

  public void setUsernameToken(Object usernameToken) {
    this.usernameToken = usernameToken;
  }

  public Security timestamp(Object timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * 
   * @return timestamp
   **/
  @ApiModelProperty(value = "")

  public Object getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Object timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Security security = (Security) o;
    return Objects.equals(this.usernameToken, security.usernameToken)
        && Objects.equals(this.timestamp, security.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usernameToken, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Security {\n");

    sb.append("    usernameToken: ").append(toIndentedString(usernameToken)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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
