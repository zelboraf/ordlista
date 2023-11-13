package com.ellara.ordlista.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dictionary")
@Getter
@Setter
@NoArgsConstructor
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "swedish", nullable = false)
    private String swedishWord;

    @Column(name = "polish", nullable = false)
    private String polishWord;

    @Column(name = "conjugation")
    private String conjugation;

    @Column(name = "partOfSpeech")
    private String partOfSpeech;

}
