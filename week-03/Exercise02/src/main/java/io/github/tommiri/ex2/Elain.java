package io.github.tommiri.ex2;

public class Elain {
    protected String nimi;

    public Elain() {
        this.nimi = "nimetön";
    }

    public Elain(String nimi) {
        this.nimi = nimi;
    }

    public void heraa() {
        System.out.println(nimi + " herää.");
    }

    public void lepaa() {
        System.out.println(nimi + " lepää.");
    }

    public void toimi() {
        System.out.println(nimi + " toimii.");
    }
}