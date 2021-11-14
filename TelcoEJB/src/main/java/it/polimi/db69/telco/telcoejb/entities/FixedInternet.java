package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("fixedinternet")
public class FixedInternet extends Service{

    @Column(name = "giga", nullable = false)
    private int giga;

    @Column(name = "gigafee", nullable = false)
    private double gigafee;
}
