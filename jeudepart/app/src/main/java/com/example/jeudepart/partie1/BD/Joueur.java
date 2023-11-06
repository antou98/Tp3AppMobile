package com.example.jeudepart.partie1.BD;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * IMPORTANT : ajouter à Module:app (build.gradle file dans Gradel scripts ) implementation 'com.j256.ormlite:ormlite-android:5.0' -  Ensuite ajouter imports import com.j256.ormlite.field.DatabaseField;
 * import com.j256.ormlite.table.DatabaseTable;
 */

@DatabaseTable(tableName = "Joueur")
public class Joueur {
    @DatabaseField(columnName = "idJoueur", generatedId = true )
    private int idJoueur;
    @DatabaseField
    private String nom;
    @DatabaseField
    private String prenom;
    @DatabaseField
    private String email;
    @DatabaseField
    private String motdepasse;
    @DatabaseField
    private String pays;

    public Joueur(int idJoueur, String nom, String prenom, String email, String motdepasse, String pays) {
        this.idJoueur = idJoueur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
        this.pays = pays;
    }

    public Joueur(String nom, String prenom, String email, String motdepasse, String pays) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
        this.pays = pays;
    }

    public Joueur() {
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "idJoueur=" + idJoueur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motdepasse='" + motdepasse + '\'' +
                ", pays='" + pays + '\'' +
                '}';
    }
}
