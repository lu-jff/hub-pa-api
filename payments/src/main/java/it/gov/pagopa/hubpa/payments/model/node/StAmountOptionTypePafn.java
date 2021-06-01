package it.gov.pagopa.hubpa.payments.model.node;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Indicates the payment criteria accepted by public administration respect to
 * the amount, i.e. if it accepts an `amount` for this payment option - equals
 * `EQ` - less `LT` - greater `GT` - any `ANY` than indicated.
 */
public enum StAmountOptionTypePafn {

  EQ("EQ"),

  LS("LS"),

  GT("GT"),

  ANY("ANY");

  private String value;

  StAmountOptionTypePafn(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StAmountOptionTypePafn fromValue(String text) {
    for (StAmountOptionTypePafn b : StAmountOptionTypePafn.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
