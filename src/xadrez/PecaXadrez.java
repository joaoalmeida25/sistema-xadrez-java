package xadrez;

import constantes.Cor;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

import java.util.Objects;

public abstract class PecaXadrez extends Peca {

    private final Cor cor;
    private int contMovimentos;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    protected boolean podeMover(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return Objects.isNull(p) || !p.getCor().equals(getCor());
    }

    protected boolean existePecaOponente(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return Objects.nonNull(p) && !p.getCor().equals(cor);
    }

    public void incrementarContMovimentos() {
        contMovimentos++;
    }

    public void decrementarContMovimentos() {
        contMovimentos--;
    }

    public PosicaoXadrez getPosicaoXadrez() {
        return PosicaoXadrez.fromPosicao(posicao);
    }

    public Cor getCor() {
        return cor;
    }

    public int getContMovimentos() {
        return contMovimentos;
    }
}
