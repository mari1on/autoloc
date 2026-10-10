package tn.esprit.autoloc.autolocapi.domain;
import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long idVehicule;
    @Column(nullable = false, unique = true, length = 20)
    String immatriculation;
    @Column(nullable = false, length = 50)
    String marque;
    @Column(nullable = false, length = 50)
    String modele;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    CategorieVehicule categorie;
    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal tarifJournalier;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    StatutVehicule statut;
    @ManyToMany(fetch = FetchType.LAZY)
    Set<Equipement> equipements = new HashSet<>();
    @OneToMany(
            mappedBy = "vehicule",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    List<Maintenance> maintenances = new ArrayList<>();
}