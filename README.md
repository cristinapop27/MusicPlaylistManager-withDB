# MusicPlaylistManager-withDB
## Overview
This project is a desktop music playlist manager built in Java using Swing for the graphical user interface and PostgreSQL for the database.
It allows users to create accounts, log in, create playlists, add songs (with file paths), and play music directly from the application, complete with audio controls (play, pause, resume, stop, volume).

## Features
- User Authentication
    - Register a new account
    - Login
- Playlist Management
    - Create new playlist
    - View and open existing playlists
- Song Management
    - Add new songs to playlists with file paths
    - Delete songs
    - View all songs in a playlist
- Audio Player
    - Play songs directly inside the app
    - Pause, resume and control volume
- Database integration
    - Storage of users, playlists, songs using PostgreSQL
- Swing UI

## Technologies Used
- Java 17
- Java Swing
- PostgreSQL
- BasicPlayer3 (for playing WAV files)
- JUnit 5 (for testing)
