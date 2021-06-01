package it.gov.pagopa.hubpa.payments.model.node;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Alphanumeric field indicating the nature of the subject; it can assume the
 * following values: - **F** : Natural person - **G** : Legal Person
 */
public enum StEntityUniqueIdentifierTypeTypePafn {

  F("F"),

  G("G");

  private String value;

  StEntityUniqueIdentifierTypeTypePafn(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StEntityUniqueIdentifierTypeTypePafn fromValue(String text) {
    for (StEntityUniqueIdentifierTypeTypePafn b : StEntityUniqueIdentifierTypeTypePafn.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
