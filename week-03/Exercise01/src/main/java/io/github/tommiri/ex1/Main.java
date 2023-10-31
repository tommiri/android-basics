package io.github.tommiri.ex1;

public class Main {
    public static void main(String[] args) {
        // Create note using default constructor and print contents
        Note emptyNote = new Note();
        System.out.println(emptyNote);

        // Set note contents and print them
        emptyNote.setContent("I set the content on this note after creating it!");
        emptyNote.setBackgroundColor(Color.GREEN);
        System.out.println(emptyNote);

        // Create note with 2-param constructor and print contents
        Note initNote = new Note("I set the content on this note when creating it!", Color.RED);
        System.out.println(initNote);
    }
}