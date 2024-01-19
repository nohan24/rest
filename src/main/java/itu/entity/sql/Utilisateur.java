package itu.entity.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

//$2a$10$SAKdm2OcuhK5qTZs24rRWeOoYpjqBUOE7k1uRhwHujwoZQsLTGlAS mdp admin

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myseq")
    @SequenceGenerator(name="myseq",sequenceName="utilisateur_seq", allocationSize = 1)
    private int id_utilisateur;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)

    private String password;
    @Column(nullable = false)

    private int genre;
    @Column(nullable = false)

    private String username;
    @Column(nullable = false)
    private Date date_naissance;
    @Column(nullable = false)
    private String roles = "USER";

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
