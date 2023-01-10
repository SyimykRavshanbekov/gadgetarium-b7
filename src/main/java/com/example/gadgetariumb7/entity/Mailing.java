package com.example.gadgetariumb7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "mailings")
@Getter
@Setter
@NoArgsConstructor
public class Mailing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailing_seq")
    @SequenceGenerator(name = "mailing_seq", sequenceName = "mailing_seq", allocationSize = 1)
    private Long id;

    private String mailingName;

    private String description;

    private String image;

    private LocalDate stockDateOfStart;
    private LocalDate stockDateOfEnd;
}
