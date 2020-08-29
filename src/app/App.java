package app;

import exception.XadrezException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partida = new PartidaXadrez();

        while (true) {
            try {
                UI.limparConsole();
                UI.imprimirTabuleiro(partida.getPecas());
                System.out.println();
                System.out.print("Peça de origem: ");
                PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);

                System.out.println();
                System.out.print("Peça de destino: ");
                PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

                PecaXadrez pecaCapturada = partida.executarMovimentoXadrez(origem, destino);
                System.out.print("Peça " + pecaCapturada + " movida para " + destino);
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }

}
