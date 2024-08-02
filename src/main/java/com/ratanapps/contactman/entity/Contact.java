package com.ratanapps.contactman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cId;

    private String name;
    private String secondName;
    private String work;
    private String email;
    private String phone;
    private String image;

    @Column(length = 5000)
    private String description;

    @ManyToOne
    private User user;

    public String printObj() {
        return "name = "+name+" secondName= "+secondName+" work="+work+" email"+email+" phone"+phone+" description"+description ;
    }
}
