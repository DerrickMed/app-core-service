package ru.sbrf.sell.appcore.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    int code;
    String name;
    boolean active = true;
}
