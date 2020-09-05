package xadrez;

import constantes.Cor;
import exception.XadrezException;
import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.security.InvalidParameterException;
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

    private PecaXadrez pecaPromovida;

    private final List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private final List<PecaXadrez> pecasCapturadas = new ArrayList<>();

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
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

    public void executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
        Posicao origem = posicaoOrigem.obterPosicao();
        Posicao destino = posicaoDestino.obterPosicao();

        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);

        Peca pecaCapturada = criarMovimento(origem, destino);

        if (testeXeque(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Voçê não pode se colocar em xeque!");
        }

//      Jogada Especial Promoção
        PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);

        pecaPromovida = null;
        if (pecaMovida instanceof Peao) {
            if ((Cor.BRANCO.equals(pecaMovida.getCor()) && destino.getLinha() == 0) ||
                    (Cor.PRETO.equals(pecaMovida.getCor()) && destino.getLinha() == 7)) {
                pecaPromovida = (PecaXadrez) tabuleiro.peca(destino);
            }
        }

        xeque = testeXeque(obterOponente(jogadorAtual));

        if (testeXequeMate(obterOponente(jogadorAtual))) {
            xequeMate = true;
        }

        trocarTurno();
    }

    public PecaXadrez reporPecaPromovida(int peca) {
        if (Objects.isNull(pecaPromovida)) {
            throw new IllegalStateException("Não há peça a ser promovida!");
        }
        if (peca != 1 && peca != 2 && peca != 3 && peca != 4) {
            throw new InvalidParameterException("Peça invalida para promoção!");
        }

        Posicao pos = pecaPromovida.getPosicaoXadrez().obterPosicao();
        Peca p = tabuleiro.removerPeca(pos);
        pecasNoTabuleiro.remove(p);

        PecaXadrez novaPeca = novaPeca(peca, pecaPromovida.getCor());
        tabuleiro.colocarPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private PecaXadrez novaPeca(int peca, Cor cor) {
        if (peca == 1) return new Torre(tabuleiro, cor);
        if (peca == 2) return new Cavalo(tabuleiro, cor);
        if (peca == 3) return new Bispo(tabuleiro, cor);
        return new Rainha(tabuleiro, cor);
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
        jogadorAtual = Cor.BRANCO.equals(jogadorAtual) ? Cor.PRETO : Cor.BRANCO;
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

//      Jogada Especial Roque Pequeno
        if (peca instanceof Rei && Objects.equals(destino.getColuna(), origem.getColuna() + 2)) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);

            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorre);
            tabuleiro.colocarPeca(torre, destinoTorre);
            torre.incrementarContMovimentos();
        }

//      Jogada Especial Roque Grande
        if (peca instanceof Rei && Objects.equals(destino.getColuna(), origem.getColuna() - 2)) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);

            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorre);
            tabuleiro.colocarPeca(torre, destinoTorre);
            torre.incrementarContMovimentos();
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

        //      Jogada Especial Roque Pequeno
        if (peca instanceof Rei && Objects.equals(destino.getColuna(), origem.getColuna() + 2)) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);

            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorre);
            tabuleiro.colocarPeca(torre, origemTorre);
            torre.decrementarContMovimentos();
        }

//      Jogada Especial Roque Grande
        if (peca instanceof Rei && Objects.equals(destino.getColuna(), origem.getColuna() - 2)) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);

            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorre);
            tabuleiro.colocarPeca(torre, origemTorre);
            torre.decrementarContMovimentos();
        }
    }

    public Cor obterOponente(Cor cor) {
        return cor.equals(Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
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

        return aplicarLogicaXequeMate(listaPeca, cor);
    }

    private boolean aplicarLogicaXequeMate(List<Peca> listaPeca, Cor cor) {
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
        colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));

        colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
        colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));

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

    public PecaXadrez getPecaPromovida() {
        return pecaPromovida;
    }
}
