package fr.ece.gestionnairetaches.model.entity;

public class Utilisateur {
    private int id;
    private String nom;     // Nouveau
    private String prenom;  // Nouveau
    private String email;
    private Role role;

    // Nouveau constructeur
    public Utilisateur(int id, String nom, String prenom, String email, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }       // Getter
    public String getPrenom() { return prenom; } // Getter
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}