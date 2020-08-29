package app;

import xadrez.PartidaXadrez;

public class App {

    public static void main(String[] args) {

        PartidaXadrez partida = new PartidaXadrez();

        UI.imprimirTabuleiro(partida.getPecas());
    }

}
