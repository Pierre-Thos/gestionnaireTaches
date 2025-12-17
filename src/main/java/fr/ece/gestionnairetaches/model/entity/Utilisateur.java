package fr.ece.gestionnairetaches.model.entity;

public class Utilisateur {
    private int id;
    private String email;
    private Role role;

    public Utilisateur(int id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }


public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role.getNom());
    }
}
