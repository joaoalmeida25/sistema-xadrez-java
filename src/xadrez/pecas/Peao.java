package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import constantes.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "♙";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if (Cor.BRANCO.equals(getCor())) {
            direcoesPossiveisCorBranca(mat, p);
        } else {
            direcoesPossiveisCorPreta(mat, p);
        }
        return mat;
    }

    private void direcoesPossiveisCorBranca(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());

        p.setValores(posicao.getLinha() - 2, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)
                && getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2) && getContMovimentos() == 0) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void direcoesPossiveisCorPreta(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());

        p.setValores(posicao.getLinha() + 2, posicao.getColuna());
        if (getTabuleiro().existePosicao(p) && !getTabuleiro().existePeca(p)
                && getTabuleiro().existePosicao(p2) && !getTabuleiro().existePeca(p2) && getContMovimentos() == 0) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && existePecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }
}
