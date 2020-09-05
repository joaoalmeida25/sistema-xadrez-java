package xadrez.pecas;

import constantes.Cor;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

import java.util.Objects;

public class Rei extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    private boolean testarRoqueTorre(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return Objects.nonNull(p) && p instanceof Torre && getCor().equals(p.getCor()) && p.getContMovimentos() == 0;
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

        if (getContMovimentos() == 0 && !partidaXadrez.getXeque()) {
            jogadaEspecialRoquePequeno(mat);
            jogadaEspecialRoqueGrande(mat);
        }

        return mat;
    }


    private void jogadaEspecialRoquePequeno(boolean[][] mat) {
        Posicao pos = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
        if (testarRoqueTorre(pos)) {
            Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
            Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
            if (Objects.isNull(getTabuleiro().peca(p1)) && Objects.isNull(getTabuleiro().peca(p2))) {
                mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
            }
        }
    }

    private void jogadaEspecialRoqueGrande(boolean[][] mat) {
        Posicao pos = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
        if (testarRoqueTorre(pos)) {
            Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
            Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
            Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
            if (Objects.isNull(getTabuleiro().peca(p1)) && Objects.isNull(getTabuleiro().peca(p2))
                    && Objects.isNull(getTabuleiro().peca(p3))) {
                mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
            }
        }
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

    @Override
    public String toString() {
        return "♔";
    }
}
