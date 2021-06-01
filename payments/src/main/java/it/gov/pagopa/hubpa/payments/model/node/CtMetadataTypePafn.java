package it.gov.pagopa.hubpa.payments.model.node;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Its a _key/value_ store fields for the exclusive use of the PA.  The data will return in the receipt (&#x60;paSendRT&#x60;)
 */
@ApiModel(description = "Its a _key/value_ store fields for the exclusive use of the PA.  The data will return in the receipt (`paSendRT`)")
@Validated
public class CtMetadataTypePafn   {
  @JsonProperty("mapEntry")
  @Valid
  private List<CtMapEntryTypePafn> mapEntry = new ArrayList<CtMapEntryTypePafn>();

  public CtMetadataTypePafn mapEntry(List<CtMapEntryTypePafn> mapEntry) {
    this.mapEntry = mapEntry;
    return this;
  }

  public CtMetadataTypePafn addMapEntryItem(CtMapEntryTypePafn mapEntryItem) {
    this.mapEntry.add(mapEntryItem);
    return this;
  }

  /**
   * Get mapEntry
   * @return mapEntry
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
@Size(min=1,max=10) 
  public List<CtMapEntryTypePafn> getMapEntry() {
    return mapEntry;
  }

  public void setMapEntry(List<CtMapEntryTypePafn> mapEntry) {
    this.mapEntry = mapEntry;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtMetadataTypePafn ctMetadataTypePafn = (CtMetadataTypePafn) o;
    return Objects.equals(this.mapEntry, ctMetadataTypePafn.mapEntry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mapEntry);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtMetadataTypePafn {\n");
    
    sb.append("    mapEntry: ").append(toIndentedString(mapEntry)).append("\n");
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

