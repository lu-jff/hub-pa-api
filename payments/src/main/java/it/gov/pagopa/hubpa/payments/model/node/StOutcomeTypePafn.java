package it.gov.pagopa.hubpa.payments.model.node;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The outcome of the operation may contain the following result string code: -
 * **OK** : operation performed successfully - **KO** : operation terminated
 * with error
 */
public enum StOutcomeTypePafn {

  OK("OK"),

  KO("KO");

  private String value;

  StOutcomeTypePafn(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static StOutcomeTypePafn fromValue(String text) {
    for (StOutcomeTypePafn b : StOutcomeTypePafn.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
