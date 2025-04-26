package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public boolean createAccount(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(String username) {
        String query = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean login(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlaylist(String username, String playlistName) {
        String query = "INSERT INTO playlists (user_id, name) VALUES ((SELECT id FROM users WHERE username = ?), ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, playlistName);
            //stmt.executeQuery();
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Playlist> getUserPlaylists(String username) {
        String query = "SELECT id, name FROM playlists WHERE user_id = (SELECT id FROM users WHERE username = ?)";
        List<Playlist> playlists = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                playlists.add(new Playlist(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlists;
    }



    public boolean addSongToPlaylist(String username, String playlistName, Song song) {
        String insertPlaylistSongQuery =
                "INSERT INTO playlist_songs (playlist_id, song_name, file_path) VALUES (" +
                        "(SELECT id FROM playlists WHERE user_id = (SELECT id FROM users WHERE username = ?) AND name = ?), ?, ?) ";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(insertPlaylistSongQuery)) {
                stmt.setString(1, username);
                stmt.setString(2, playlistName);
                stmt.setString(3, song.getName());
                stmt.setString(4, song.getFilePath());
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public List<String> getSongsInPlaylist(String username, String playlistName) {
        String query = "SELECT song_name FROM playlist_songs WHERE playlist_id = (SELECT id FROM playlists WHERE user_id = (SELECT id FROM users WHERE username = ?) AND name = ?)";
        List<String> songs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, playlistName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                songs.add(rs.getString("song_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public boolean removeSongFromPlaylist(String username, String playlistName, String songName) {
        String sql = "DELETE FROM playlist_songs "
                + "WHERE playlist_id = (SELECT id FROM playlists "
                + "                     WHERE user_id = (SELECT id FROM users WHERE username = ?) "
                + "                       AND name = ?) "
                + "  AND song_name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, playlistName);
            stmt.setString(3, songName);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getSongFilePath(String username, String playlistName, String songName) {

        String baseDirectory = "C:\\Users\\crist\\OneDrive\\Desktop\\utcn\\an II sem I\\oop\\proiect\\";
        System.out.println(username + " " + playlistName + " " + songName);
        String relativePath = null;

        String sql = "SELECT file_path " +
                "FROM playlist_songs " +
                "WHERE playlist_id = (SELECT id FROM playlists WHERE user_id = " +
                "(SELECT id FROM users WHERE username = ?) AND name = ?) " +
                "  AND song_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, playlistName);
            stmt.setString(3, songName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    relativePath = rs.getString("file_path");
                    System.out.println(relativePath);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        if (relativePath != null && !relativePath.isEmpty()) {

            System.out.println(new File(baseDirectory+relativePath).exists());
            return baseDirectory + relativePath;
        } else {
            return null;
        }
    }

}
