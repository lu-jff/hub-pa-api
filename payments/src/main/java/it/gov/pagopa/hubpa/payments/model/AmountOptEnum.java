package it.gov.pagopa.hubpa.payments.model;

import lombok.Getter;

@Getter
public enum AmountOptEnum {
  EQ("EQ"), LS("LS"), GT("GT"), ANY("ANY");

  private final String value;

  AmountOptEnum(String value) {
    this.value = value;
  }
}
