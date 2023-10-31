package io.github.tommiri.ex2;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Lintu haukka = new Lintu("Haukka");
        Kala kilpikonna = new Kala("Kilpikonna");

        haukka.heraa();
        kilpikonna.heraa();

        haukka.toimi();
        kilpikonna.toimi();

        haukka.lepaa();
        kilpikonna.lepaa();

        System.out.println();

        ArrayList<Elain> elaimet = new ArrayList<>();
        elaimet.add(haukka);
        elaimet.add(kilpikonna);

        for (Elain e : elaimet) {
            e.heraa();
        }
        for (Elain e : elaimet) {
            e.toimi();
        }
        for (Elain e : elaimet) {
            e.lepaa();
        }
    }
}