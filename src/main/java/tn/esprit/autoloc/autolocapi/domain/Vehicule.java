package tn.esprit.autoloc.autolocapi.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "employes"})
    Agence agence;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Equipement> equipements = new HashSet<>();
    //sm10 - Liste des maintenances associées au véhicule
    @OneToMany(
            mappedBy = "vehicule",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    List<Maintenance> maintenances = new ArrayList<>();

    //sm10 - Liste des réservations associées au véhicule
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Reservation> reservations = new ArrayList<>();
}