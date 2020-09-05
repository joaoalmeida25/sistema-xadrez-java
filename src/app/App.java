package app;

import exception.XadrezException;
import xadrez.PartidaXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partida = new PartidaXadrez();

        while (!partida.getXequeMate()) {
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

                if (Objects.nonNull(partida.getPecaPromovida())) {
                    System.out.println("Escolha uma peça para promoção: ");
                    System.out.println();
                    System.out.println("1 - Torre\n2 - Cavalo\n3 - Bispo\n4 - Rainha");
                    System.out.println();
                    System.out.print("N° da peça: ");

                    int peca = sc.nextInt();

                    while (peca != 1 && peca != 2 && peca != 3 && peca != 4) {
                        System.out.println();
                        System.out.print("Digite novamente o n° da peça: ");

                        peca = sc.nextInt();
                    }

                    partida.reporPecaPromovida(peca);
                    partida.executarMovimentoXadrez(origem, destino);
                }
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limparConsole();
        UI.imprimirPartida(partida, partida.getPecasCapturadas());
    }

}
