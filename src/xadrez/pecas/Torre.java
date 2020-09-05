package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import constantes.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "♖";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        norte(mat, p);
        sul(mat, p);
        oeste(mat, p);
        leste(mat, p);

        return mat;
    }

    private void norte(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void sul(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void oeste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void leste(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }
}
