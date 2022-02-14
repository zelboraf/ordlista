package com.ellara.ordlista.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long dictionaryId;

    @Column(name = "swedish")
    private String swedishWord;

    @Column(name = "polish")
    private String polishWord;
}



