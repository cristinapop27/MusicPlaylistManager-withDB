package view;

import model.Playlist;
import model.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlaylistsPageView extends JFrame {
    private String username;
    private UserManager userManager;
    private DefaultListModel<Playlist> playlistListModel;

    public PlaylistsPageView(String username) {
        this.username = username;
        this.userManager = new UserManager();
        this.playlistListModel = new DefaultListModel<>();

        Color background = Color.decode("#2B3040"); //navy
        Color fullButton = Color.decode("#5A24A6"); //darker blue
        Color textColor = Color.decode("#FEF9F2"); //white
        Color button = Color.decode("#9F96D9"); //purpleish

        // container
        setTitle("Your playlists");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        // titlu
        JLabel titleLabel = new JLabel("Your Playlists", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(background);
        titleLabel.setForeground(textColor);
        add(titleLabel, BorderLayout.NORTH);

        // lista + scroll
        JList<Playlist> playlistList = new JList<>(playlistListModel);
        playlistList.setCellRenderer(new CustomListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(playlistList);
        playlistList.setBackground(background);
        playlistList.setOpaque(false);

        scrollPane.getViewport().setBackground(background);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        loadUserPlaylists();

        // add playlist button
        JButton addPlaylistButton = new JButton("Create New Playlist");

        addPlaylistButton.addActionListener( e -> {
            String newPlaylist = JOptionPane.showInputDialog("Enter new playlist name");
            if (newPlaylist != null && !newPlaylist.trim().isEmpty()) {
                userManager.createPlaylist(username, newPlaylist);
                loadUserPlaylists();
            }
        });
        addPlaylistButton.setBackground(fullButton);
        addPlaylistButton.setForeground(textColor);

        // back button
        JButton backButton = new JButton("Log Out");
        backButton.addActionListener(e -> {
            new LoginPageView(userManager);
            dispose();
        });
        backButton.setBackground(fullButton);
        backButton.setForeground(textColor);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(addPlaylistButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);


        playlistList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = playlistList.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                        Playlist selectedPlaylist = playlistListModel.getElementAt(index);
                        new SongsPageView(username, selectedPlaylist);
                        dispose();
                    }
                }
            }
        });
        setVisible(true);
    }

    private void loadUserPlaylists() {
        // sterge tot
        playlistListModel.clear();

        // din baza de date
        List<Playlist> playlists = userManager.getUserPlaylists(username);

        for (Playlist playlist : playlists) {
            playlistListModel.addElement(playlist);
        }
    }

    private static class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Color button1 = Color.decode("#9F96D9");
            Color button2 = Color.decode("#CBC5D9");
            Color selected = Color.decode("#BDF2AE");

            if (isSelected) {
                label.setBackground(selected);
                label.setForeground(Color.BLACK);
            } else {
                label.setBackground(index % 2 == 0 ? button1 : button2);
                label.setForeground(Color.BLACK);
            }
            label.setOpaque(true);
            return label;
        }
    }
}
