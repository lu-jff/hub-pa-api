package it.gov.pagopa.hubpa.payments.model.node;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets stIdTransfer_type_pafn
 */
public enum StIdTransferTypePafn {

  NUMBER_1(1),

  NUMBER_2(2),

  NUMBER_3(3),

  NUMBER_4(4),

  NUMBER_5(5);

  private Integer value;

  StIdTransferTypePafn(Integer value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StIdTransferTypePafn fromValue(String text) {
    for (StIdTransferTypePafn b : StIdTransferTypePafn.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
