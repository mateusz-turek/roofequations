package project1.roofequations.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "admins")
public class AdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    public AdminModel(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
