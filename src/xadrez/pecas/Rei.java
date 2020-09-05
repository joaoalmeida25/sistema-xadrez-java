package xadrez.pecas;

import constantes.Cor;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

    public Rei(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "♔";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        norte(mat, p);
        sul(mat, p);
        oeste(mat, p);
        leste(mat, p);
        noroeste(mat, p);
        nordeste(mat, p);
        sudoeste(mat, p);
        sudeste(mat, p);

        return mat;
    }

    private void norte(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void sul(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void oeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void leste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

    }

    private void noroeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void nordeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void sudoeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void sudeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }
}
