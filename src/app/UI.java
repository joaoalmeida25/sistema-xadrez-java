package app;

import constantes.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.io.IOException;
import java.util.*;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
        try {
            String s = sc.nextLine();
            int linha = Integer.parseInt(s.substring(1));
            char coluna = s.charAt(0);
            return new PosicaoXadrez(linha, coluna);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler posição de xadrez. Insira os argumentos de a1 a h8");
        }
    }

    public static void imprimirPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> pecasCapturadas) {
        imprimirTabuleiro(partidaXadrez.getPecas());
        System.out.println();
        imprimirPecasCapturadas(pecasCapturadas);
        System.out.println();
        System.out.println("Turno: " + partidaXadrez.getTurno());
        System.out.println("Esperando o jogador da cor: " + partidaXadrez.getJogadorAtual());
    }

    public static void imprimirTabuleiro(PecaXadrez[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void imprimirTabuleiro(PecaXadrez[][] pecas, boolean[][] possiveisMovimentos) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], possiveisMovimentos[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void imprimirPeca(PecaXadrez peca, boolean mostrarMovimento) {
        if (mostrarMovimento) {
            System.out.print(ANSI_CYAN_BACKGROUND);
        }
        if (Objects.isNull(peca)) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (peca.getCor() == Cor.BRANCA) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            } else {
                System.out.print(ANSI_PURPLE + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    public static void imprimirPecasCapturadas(List<PecaXadrez> pecas) {
        System.out.println("Peças capturadas");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(pecas.stream().filter(x -> x.getCor().equals(Cor.BRANCA)).toArray()));
        System.out.print(ANSI_RESET);

        System.out.print("Pretas: ");
        System.out.print(ANSI_PURPLE);
        System.out.println(Arrays.toString(pecas.stream().filter(x -> x.getCor().equals(Cor.PRETA)).toArray()));
        System.out.print(ANSI_RESET);
    }

    public static void limparConsole() {
        try {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            limparConsole(System.getProperty("os.name"));
        } catch (IOException e) {
            System.out.print("Erro ao limpar console: " + e.getMessage());
        }
    }

    private static void limparConsole(String sistema) throws IOException {
        if (sistema.equals("Linux")) {
            Runtime.getRuntime().exec("clear");
        } else {
            Runtime.getRuntime().exec("cls");
        }
    }
}
