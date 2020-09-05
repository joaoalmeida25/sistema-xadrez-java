package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import constantes.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

    public Cavalo(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "♘";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        possibilidade1(mat, p);
        possibilidade2(mat, p);
        possibilidade3(mat, p);
        possibilidade4(mat, p);
        possibilidade5(mat, p);
        possibilidade6(mat, p);
        possibilidade7(mat, p);
        possibilidade8(mat, p);

        return mat;
    }

    private void possibilidade1(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void possibilidade2(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void possibilidade3(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void possibilidade4(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

    }

    private void possibilidade5(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void possibilidade6(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void possibilidade7(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }

    private void possibilidade8(boolean[][] mat, Posicao p) {
        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if (getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
    }
}
