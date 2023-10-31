package io.github.tommiri.ex2;

public class Lintu extends Elain {
    public Lintu(String nimi) {
        super(nimi);
    }

    @Override
    public void toimi() {
        System.out.println(this.nimi + " lentää");
    }
}