package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 0 || colunas < 0) {
            throw new TabuleiroException("Erro ao criar tabuleiro! É necessario pelo menos 1 linha e 1 coluna.");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public Peca peca(int linha, int coluna) {
        if (!existePosicao(linha, coluna)) {
            throw new TabuleiroException("Não existe esta posição no tabuleiro!");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void posicaoPeca(Peca peca, Posicao posicao) {
        if (!existePosicao(posicao)) {
            throw new TabuleiroException("Já existe uma peça na posição: " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    private boolean existePosicao(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean existePosicao(Posicao posicao) {
        return existePosicao(posicao.getLinha(), posicao.getColuna());
    }

    public boolean existePeca(Posicao posicao) {
        if (!existePosicao(posicao.getLinha(), posicao.getColuna())) {
            throw new TabuleiroException("Não existe esta posição no tabuleiro!");
        }
        return peca(posicao) != null;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

}
