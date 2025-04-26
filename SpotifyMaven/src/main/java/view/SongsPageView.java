package view;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SongsPageView extends JFrame {
    private String username;
    private UserManager userManager;
    //private String playlistName;
    private DefaultListModel<String> songListModel;
    private JPopupMenu popupMenu;
    private JMenuItem deleteItem;
    private JList<String> songJList ;
    private AudioPlayer audioPlayer;
    private Playlist playlist;

    private final Color background = Color.decode("#2B3040"); // Navy
    private final Color fullButton = Color.decode("#5A24A6"); // Darker Blue
    private final Color textColor = Color.decode("#FEF9F2"); // White
    private final Color button1 = Color.decode("#9F96D9"); // Purple-ish
    private final Color button2 = Color.decode("#CBC5D9");

    public SongsPageView(String username, Playlist playlist) {
        this.username = username;
        //this.playlistName = playlistName;
        this.userManager = new UserManager();
        this.songListModel = new DefaultListModel<>();
        this.songJList = new JList<>(songListModel);
        this.audioPlayer = new AudioPlayer();
        this.playlist=playlist;

        // frame
        setTitle("Playlist: " + playlist.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        // title
        JLabel titleLabel = new JLabel("Songs in \"" + playlist.getName() + "\"", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(background);
        titleLabel.setForeground(textColor);
        add(titleLabel, BorderLayout.NORTH);

        // song list

        songJList.setCellRenderer(new CustomListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(songJList);
        scrollPane.getViewport().setBackground(background);
        add(scrollPane, BorderLayout.CENTER);
        songJList.setOpaque(false);


        loadSongs();

        //menu when user right-clicks on a song
        popupMenu = new JPopupMenu();
        deleteItem = new JMenuItem("Delete Song");
        deleteItem.addActionListener(e -> handleDelete());
        popupMenu.add(deleteItem);

        // show popup on right-click
        songJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }
        });

        //control song when playing
        JPanel audioControlPanel = new JPanel();
        audioControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        audioControlPanel.setBackground(background);

        //play
        JButton playButton = new JButton("Play");
        playButton.setBackground(fullButton);
        playButton.setForeground(textColor);
        playButton.addActionListener(e -> {
            int idx = songJList.getSelectedIndex();
            if (idx >= 0) {
                String songName = songListModel.getElementAt(idx);

                String mp3Path = userManager.getSongFilePath(username, playlist.getName(), songName);

                audioPlayer.play(mp3Path);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a song to play.", "No Song Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        audioControlPanel.add(playButton);

        // resume
        JButton pauseButton = new JButton("Pause");
        pauseButton.setBackground(fullButton);
        pauseButton.setForeground(textColor);
        pauseButton.addActionListener(e -> audioPlayer.pause());
        audioControlPanel.add(pauseButton);

        // resume
        JButton resumeButton = new JButton("Resume");
        resumeButton.setBackground(fullButton);
        resumeButton.setForeground(textColor);
        resumeButton.addActionListener(e -> audioPlayer.resume());
        audioControlPanel.add(resumeButton);

        // stop and play from beginning
        JButton stopButton = new JButton("Play from beginning");
        stopButton.setBackground(fullButton);
        stopButton.setForeground(textColor);
        stopButton.addActionListener(e ->
        {
            audioPlayer.stop();
            int idx = songJList.getSelectedIndex();
            if (idx >= 0) {
                String songName = songListModel.getElementAt(idx);

                String mp3Path = userManager.getSongFilePath(username, playlist.getName(), songName);

                audioPlayer.play(mp3Path);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a song to play.", "No Song Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        audioControlPanel.add(stopButton);

        JSlider volumeSlider = new JSlider(0, 100, 80);
        volumeSlider.addChangeListener(e -> {
            double gain = volumeSlider.getValue() / 100.0;
            audioPlayer.setVolume(gain);
        });
        audioControlPanel.add(new JLabel("Volume:"));
        audioControlPanel.add(volumeSlider);

        add(audioControlPanel, BorderLayout.NORTH);

        // add song button
        JButton addSongButton = new JButton("Add New Song");
        addSongButton.addActionListener(e -> {
            String newSongName = JOptionPane.showInputDialog(SongsPageView.this, "Enter Song Name:");
            if (newSongName != null && !newSongName.trim().isEmpty()) {

                String filePath = JOptionPane.showInputDialog(SongsPageView.this, "Enter Song Path:");
                if (filePath != null && !filePath.trim().isEmpty()) {

                    Song newSong = new Song(newSongName.trim(), filePath.trim());
                    userManager.addSongToPlaylist(username, playlist.getName(), newSong);
                    loadSongs();
                } else {
                    JOptionPane.showMessageDialog(SongsPageView.this,
                            "File path cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(SongsPageView.this,
                        "Song name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        addSongButton.setBackground(fullButton);
        addSongButton.setForeground(textColor);


        // back button
        JButton backButton = new JButton("Back to Playlists");
        backButton.addActionListener(e -> {
            new PlaylistsPageView(username);
            dispose();
        });
        backButton.setBackground(fullButton);
        backButton.setForeground(textColor);


        // 2 buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        buttonPanel.setBackground(background);
        buttonPanel.add(addSongButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadSongs() {
        songListModel.clear();

        List<String> songs = userManager.getSongsInPlaylist(username, playlist.getName());
        if (songs != null) {
            for (String song : songs) {
                songListModel.addElement(song);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load songs for the playlist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete(){
        int selectedIndex= songJList.getSelectedIndex();
        if (selectedIndex >= 0) {
            String songName = songListModel.get(selectedIndex);
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete '" + songName + "'?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {

                userManager.removeSongFromPlaylist(username, playlist.getName(), songName);
                loadSongs();
            }
        }
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            int index = songJList.locationToIndex(e.getPoint());
            if (index >= 0) {
                //select item to be deleted
                songJList.setSelectedIndex(index);
            }
            popupMenu.show(songJList, e.getX(), e.getY());
        }
    }

    private class CustomListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (isSelected) {
                label.setBackground(fullButton);
                label.setForeground(textColor);
            } else {
                label.setBackground(index % 2 == 0 ? button1 : button2);
                label.setForeground(Color.BLACK);
            }

            label.setOpaque(true);
            return label;
        }
    }
}
