import model.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserManagerTest {
    private static UserManager userManager;

    @BeforeAll
    static void setupDatabase() {
        userManager = new UserManager();
    }

    @Test
    void testCreateAccount() {
        String username = "testuser_unique";
        String password = "password123";

        boolean result = userManager.createAccount(username, password);

        Assertions.assertTrue(result, "Account creation should succeed for a unique username.");

    }

    @Test
    void deleteAccount() {
        String username = "testuser_unique";
        boolean result = userManager.deleteAccount(username);
        Assertions.assertTrue(result, "Account deletion should succeed.");
    }

    @Test
    void testLoginValidCredentials() {
        userManager.createAccount("testuser1", "password123");
        boolean loginSuccess = UserManager.login("testuser", "password123");
        Assertions.assertTrue(loginSuccess, "Login should succeed with valid credentials.");
        userManager.deleteAccount("testuser1");
    }

    @Test
    void testCreatePlaylist() {
        userManager.createPlaylist("testuser", "My Playlist");

        List<Playlist> playlists = userManager.getUserPlaylists("testuser");

        boolean playlistExists = playlists.stream()
                .anyMatch(playlist -> "My Playlist".equals(playlist.getName()));

        Assertions.assertTrue(playlistExists, "Playlist should be created and exist in the user's playlists.");
    }


}
