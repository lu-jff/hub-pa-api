package it.gov.pagopa.hubpa.api.ente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefpIntrospect {
  Boolean active;
  String level;
  RefpSpid user;
}