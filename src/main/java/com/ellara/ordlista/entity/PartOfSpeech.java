package com.ellara.ordlista.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "partsOfSpeech")
@Getter
@Setter
@NoArgsConstructor
public class PartOfSpeech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String partOfSpeech;

}
