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
 * Structure containing the details of possible tranfer payments. Currently set
 * at 5 eligible payments per single position. Where each &#x60;transfer&#x60;
 * items contains : - &#x60;idTransfer&#x60; : index of the list (from
 * &#x60;1&#x60; to &#x60;5&#x60;) - &#x60;transferAmount&#x60; : amount -
 * &#x60;fiscalCodePA&#x60; : Tax code of the public administration -
 * &#x60;IBAN&#x60; : contains the IBAN of the account to be credited -
 * &#x60;remittanceInformation&#x60; : reason for payment (_alias_
 * &#x60;causaleVersamento&#x60;) - &#x60;transferCategory&#x60; : contains
 * taxonomic code, composed by &#x60;Codice tipo Ente
 * Creditore&#x60;+&#x60;Progressivo macro area&#x60;+&#x60;Codice tipologia
 * servizio&#x60;+&#x60;Motivo Giuridico&#x60; ( ex. &#x60;0101002IM&#x60; ) |
 * Segment | Regex |Example |
 * |-----------------------------|-----------------------------|--------|
 * |Codice tipo Ente Creditore | &#x60;\\d{2}&#x60; | 01 | |Progressivo macro
 * area | &#x60;\\d{2}&#x60; | 01 | |Codice tipologia servizio |
 * &#x60;\\d{2}&#x60; | 002 | |Motivo Giuridico | &#x60;\\w{2}&#x60; | IM |
 */
@ApiModel(description = "Structure containing the details of possible tranfer payments.  Currently set at 5 eligible payments per single position.  Where each `transfer` items contains :  - `idTransfer` : index of the list (from `1` to `5`)  - `transferAmount` : amount  - `fiscalCodePA` : Tax code of the public administration - `IBAN` : contains the IBAN of the account to be credited - `remittanceInformation` : reason for payment (_alias_ `causaleVersamento`) - `transferCategory` : contains taxonomic code, composed by `Codice tipo Ente Creditore`+`Progressivo macro area`+`Codice tipologia servizio`+`Motivo Giuridico` ( ex. `0101002IM` )  | Segment                     | Regex                       |Example | |-----------------------------|-----------------------------|--------| |Codice tipo Ente Creditore   | `\\d{2}`                     | 01     | |Progressivo macro area       | `\\d{2}`                     | 01     | |Codice tipologia servizio    | `\\d{2}`                     | 002    | |Motivo Giuridico             | `\\w{2}`                     | IM     |")
@Validated
public class CtTransferListPATypePafn {
  @JsonProperty("transfer")
  @Valid
  private List<CtTransferPATypePafn> transfer = new ArrayList<CtTransferPATypePafn>();

  public CtTransferListPATypePafn transfer(List<CtTransferPATypePafn> transfer) {
    this.transfer = transfer;
    return this;
  }

  public CtTransferListPATypePafn addTransferItem(CtTransferPATypePafn transferItem) {
    this.transfer.add(transferItem);
    return this;
  }

  /**
   * Get transfer
   * 
   * @return transfer
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid
  @Size(min = 1, max = 5)
  public List<CtTransferPATypePafn> getTransfer() {
    return transfer;
  }

  public void setTransfer(List<CtTransferPATypePafn> transfer) {
    this.transfer = transfer;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtTransferListPATypePafn ctTransferListPATypePafn = (CtTransferListPATypePafn) o;
    return Objects.equals(this.transfer, ctTransferListPATypePafn.transfer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transfer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtTransferListPATypePafn {\n");

    sb.append("    transfer: ").append(toIndentedString(transfer)).append("\n");
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
