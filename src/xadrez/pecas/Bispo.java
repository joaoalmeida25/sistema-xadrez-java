package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import constantes.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

    public Bispo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "♗";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        noroeste(mat, p);
        nordeste(mat, p);
        sudoeste(mat, p);
        sudeste(mat, p);

        return mat;
    }

    private void noroeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() - 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void nordeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() - 1, p.getColuna() + 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void sudoeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void sudeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() + 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }
}
