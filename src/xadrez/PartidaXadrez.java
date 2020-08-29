package xadrez;

import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        configInicial();
    }

    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return matriz;
    }

    private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(linha, coluna).toPosicao());
    }

    public void configInicial() {
        colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));

        colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));
    }
}
