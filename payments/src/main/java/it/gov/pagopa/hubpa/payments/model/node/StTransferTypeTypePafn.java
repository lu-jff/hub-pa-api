package it.gov.pagopa.hubpa.payments.model.node;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets stTransferType_type_pafn
 */
public enum StTransferTypeTypePafn {

  POSTAL("POSTAL");

  private String value;

  StTransferTypeTypePafn(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StTransferTypeTypePafn fromValue(String text) {
    for (StTransferTypeTypePafn b : StTransferTypeTypePafn.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
