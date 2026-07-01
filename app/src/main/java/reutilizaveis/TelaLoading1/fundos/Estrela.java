package reutilizaveis.telaloading1.fundos;

import java.util.Random;

public class Estrela {
    public float x, y;
    public float velocidadeY;
    public float tamanho;
    public int alpha;
    private boolean esmaecendo = true;

    public Estrela(int larguraTela, int alturaTela, Random random) {
        // Posiciona aleatoriamente na tela
        this.x = random.nextFloat() * larguraTela;
        this.y = random.nextFloat() * alturaTela;
        // Velocidade leve para subir
        this.velocidadeY = (random.nextFloat() * 0.8f) + 0.2f;
        // Tamanhos variados para dar sensação de profundidade (efeito 3D paralelo)
        this.tamanho = (random.nextFloat() * 4f) + 2f;
        this.alpha = random.nextInt(150) + 50;
    }

    public void atualizar(int alturaTela) {
        // Move a partícula para cima
        this.y -= velocidadeY;
        
        // Efeito de brilho oscilante individual (shimmer)
        if (esmaecendo) {
            alpha -= 2;
            if (alpha <= 40) esmaecendo = false;
        } else {
            alpha += 2;
            if (alpha >= 200) esmaecendo = true;
        }

        // Se sair pelo topo da tela, ressurge na base com novos valores
        if (this.y < 0) {
            this.y = alturaTela;
            this.alpha = 50;
        }
    }
}
