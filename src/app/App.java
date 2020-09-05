package app;

import exception.XadrezException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.*;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partida = new PartidaXadrez();

        while (true) {
            try {
                UI.limparConsole();
                UI.imprimirPartida(partida, partida.getPecasCapturadas());
                System.out.println();
                System.out.print("Peça de origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

                boolean[][] possiveisMovimentos = partida.possiveisMovimentosPeca(origem);
                UI.limparConsole();
                UI.imprimirTabuleiro(partida.getPecas(), possiveisMovimentos);

                System.out.println();
                System.out.print("Peça de destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

                partida.executarMovimentoXadrez(origem, destino);
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }

}
