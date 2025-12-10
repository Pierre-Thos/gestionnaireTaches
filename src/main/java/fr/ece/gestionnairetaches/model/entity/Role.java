package fr.ece.gestionnairetaches.model.entity;

public class Role {
    private int id;
    private String nom;
    public Role(int id, String nom) { this.id = id; this.nom = nom; }
    public String getNom() { return nom; }
}