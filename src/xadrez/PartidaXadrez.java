package xadrez;

import exception.XadrezException;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.util.Objects;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        configInicial();
    }

    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] matriz = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                matriz[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return matriz;
    }

    public boolean[][] possiveisMovimentosPeca(PosicaoXadrez posicaoOrigem) {
        Posicao posicao = posicaoOrigem.obterPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
        Posicao origem = posicaoOrigem.obterPosicao();
        Posicao destino = posicaoDestino.obterPosicao();

        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);


        Peca pecaCapturada = criarMovimento(origem, destino);
        return (PecaXadrez) pecaCapturada;
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
            throw new XadrezException("Está peça não pode ser movida até a posição de destino!");
        }
    }

    private Peca criarMovimento(Posicao origem, Posicao destino) {
        Peca peca = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.colocarPeca(peca, destino);
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao origem) {
        if (!tabuleiro.existePeca(origem)) {
            throw new XadrezException("Não existe peça na posição " + origem.toString());
        }
        if (!tabuleiro.peca(origem).existePossivelMovimento()) {
            throw new XadrezException("Não há movimentos possíveis para esta peça!");
        }
    }

    private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(linha, coluna).obterPosicao());
    }

    public void configInicial() {
        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('e', 1, new Rainha(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));

//        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 8, new Rainha(tabuleiro, Cor.PRETA));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));

        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA));
    }
}
