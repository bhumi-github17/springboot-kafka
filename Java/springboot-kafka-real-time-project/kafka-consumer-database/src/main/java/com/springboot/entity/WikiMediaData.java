package com.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wikimedia_recentchange")
@Getter
@Setter
public class WikiMediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // To store large data we use this annotation
    @Lob
    @Column(name = "wikiEventData", columnDefinition = "LONGTEXT")
    private String wikiEventData;


}
