package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Attributes
    private int id;
    private String username;
    private String email;
    private String password;
    private String subscriptionType;
    private List<Playlist> playlists;

    public User(int id, String username, String email, String password, String subscriptionType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.subscriptionType = subscriptionType;
        this.playlists = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
