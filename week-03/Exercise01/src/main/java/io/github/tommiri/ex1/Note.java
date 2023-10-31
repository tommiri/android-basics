package io.github.tommiri.ex1;

public class Note {
    private String content;
    private Color backgroundColor;

    public Note() {
        this.content = "";
        this.backgroundColor = Color.BLACK;
    }

    public Note(String content, Color backgroundColor) {
        this.content = content;
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("Content: %s\nBackground color: %s", getContent(), getBackgroundColor());
    }
}