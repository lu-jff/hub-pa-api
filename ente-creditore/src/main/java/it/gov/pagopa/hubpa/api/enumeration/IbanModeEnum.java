package it.gov.pagopa.hubpa.api.enumeration;

import lombok.Getter;

@Getter
public enum IbanModeEnum {
    FULL("1"),
    BANKING("2"),
    POSTAL("3");

    private final String value;

    IbanModeEnum(String value) {
        this.value = value;
    }
}

