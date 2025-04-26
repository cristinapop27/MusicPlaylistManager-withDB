package model;

public class Song {
    private String name;
    private String filePath;

    // Constructor
    public Song(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    // Setters (Optional, if you want the fields to be modifiable)
    public void setName(String name) {
        this.name = name;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
