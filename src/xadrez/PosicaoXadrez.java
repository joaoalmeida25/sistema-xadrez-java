package xadrez;

import exception.XadrezException;
import tabuleiro.Posicao;

public class PosicaoXadrez {

    private int linha;
    private char coluna;

    public PosicaoXadrez(int linha, char coluna) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
            throw new XadrezException("Não foi possivel instanciar a posição" +
                    " (Linha - " + linha + " e Coluna - " + coluna + ")");
        }
        this.linha = linha;
        this.coluna = coluna;
    }

    protected Posicao obterPosicao() {
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static PosicaoXadrez fromPosicao(Posicao posicao) {
        return new PosicaoXadrez(8 - posicao.getLinha(), (char) ('a' + posicao.getColuna()));
    }

    public int getLinha() {
        return linha;
    }

    public char getColuna() {
        return coluna;
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
