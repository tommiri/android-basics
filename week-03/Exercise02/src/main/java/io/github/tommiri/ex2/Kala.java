package io.github.tommiri.ex2;

public class Kala extends Elain {
    public Kala(String nimi) {
        super(nimi);
    }

    @Override
    public void toimi() {
        System.out.println(this.nimi + " ui.");
    }
}