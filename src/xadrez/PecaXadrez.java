package xadrez;

import constantes.Cor;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

import java.util.Objects;

public abstract class PecaXadrez extends Peca {

    private final Cor cor;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    protected boolean existePecaOponente(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return Objects.nonNull(p) && !p.getCor().equals(cor);
    }

    public PosicaoXadrez getPosicaoXadrez() {
        return PosicaoXadrez.fromPosicao(posicao);
    }

    public Cor getCor() {
        return cor;
    }
}
