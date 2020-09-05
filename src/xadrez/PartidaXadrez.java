package xadrez;

import constantes.Cor;
import exception.XadrezException;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private final Tabuleiro tabuleiro;
    private boolean xeque;
    private boolean xequeMate;

    private final List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private final List<PecaXadrez> pecasCapturadas = new ArrayList<>();

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCA;
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

        if (testeXeque(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Voçê não pode se colocar em xeque!");
        }

        xeque = testeXeque(obterOponente(jogadorAtual));

        if (testeXequeMate(obterOponente(jogadorAtual))) {
            xequeMate = true;
        }

        trocarTurno();

        return (PecaXadrez) pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao origem) {
        if (!tabuleiro.existePeca(origem)) {
            throw new XadrezException("Não existe peça na posição " + origem.toString());
        }
        if (!Objects.equals(jogadorAtual, ((PecaXadrez) tabuleiro.peca(origem)).getCor())) {
            throw new XadrezException("A peça selecionada não é sua!");
        }
        if (!tabuleiro.peca(origem).existePossivelMovimento()) {
            throw new XadrezException("Não há movimentos possíveis para esta peça!");
        }
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
            throw new XadrezException("Está peça não pode ser movida até a posição de destino!");
        }
    }


    private void trocarTurno() {
        turno++;
        jogadorAtual = Cor.BRANCA.equals(jogadorAtual) ? Cor.PRETA : Cor.BRANCA;
    }

    private Peca criarMovimento(Posicao origem, Posicao destino) {
        PecaXadrez peca = (PecaXadrez) tabuleiro.removerPeca(origem);
        peca.incrementarContMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);

        tabuleiro.colocarPeca(peca, destino);

        if (Objects.nonNull(pecaCapturada)) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add((PecaXadrez) pecaCapturada);
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        PecaXadrez peca = (PecaXadrez) tabuleiro.removerPeca(destino);
        peca.decrementarContMovimentos();
        tabuleiro.colocarPeca(peca, origem);

        if (Objects.nonNull(pecaCapturada)) {
            tabuleiro.colocarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    public Cor obterOponente(Cor cor) {
        return cor.equals(Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
    }

    private PecaXadrez rei(Cor cor) {
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor().equals(cor))
                .collect(Collectors.toList());
        for (Peca peca : lista) {
            if (peca instanceof Rei) {
                return (PecaXadrez) peca;
            }
        }
        throw new IllegalStateException("Não existe o rei " + cor + " no tabuleiro!");
    }

    private boolean testeXeque(Cor cor) {
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().obterPosicao();
        List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor()
                .equals(obterOponente(cor))).collect(Collectors.toList());

        for (Peca peca : pecasOponente) {
            boolean[][] mat = peca.possiveisMovimentos();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeXequeMate(Cor cor) {

        if (!testeXeque(cor)) {
            return false;
        }
        List<Peca> listaPeca = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor().equals(cor))
                .collect(Collectors.toList());

        for (Peca peca : listaPeca) {
            boolean[][] mat = peca.possiveisMovimentos();
            for (int i = 0; i < tabuleiro.getLinhas(); i++) {
                for (int j = 0; j < tabuleiro.getColunas(); j++) {
                    if (mat[i][j]) {
                        Posicao origem = ((PecaXadrez) peca).getPosicaoXadrez().obterPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = criarMovimento(origem, destino);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if (!testeXeque(cor)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.colocarPeca(peca, new PosicaoXadrez(linha, coluna).obterPosicao());
        pecasNoTabuleiro.add(peca);
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

//        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA));
//        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA));

    }

    public List<PecaXadrez> getPecasCapturadas() {
        return pecasCapturadas;
    }

    public boolean getXeque() {
        return xeque;
    }

    public boolean getXequeMate() {
        return xequeMate;
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

}
