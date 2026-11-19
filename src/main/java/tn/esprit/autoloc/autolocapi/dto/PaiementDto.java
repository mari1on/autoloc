package tn.esprit.autoloc.autolocapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.autolocapi.domain.ModePaiement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDto {

    private Long idPaiement;
    private BigDecimal montant;
    private LocalDate datePaiement;
    private ModePaiement modePaiement;
    private Long idContrat;
}
