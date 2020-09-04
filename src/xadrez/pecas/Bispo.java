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

        return mat;
    }
}
