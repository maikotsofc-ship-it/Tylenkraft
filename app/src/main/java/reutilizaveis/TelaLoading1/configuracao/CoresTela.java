package reutilizaveis.telaloading1.configuracao;

import android.graphics.Color;

public class CoresTela {
    // Padrão Dark Elegante
    public int corFundo = Color.parseColor("#0F111A");       // Azul quase preto profundo
    public int corProgresso = Color.parseColor("#00E676");     // Verde neon/cyberpunk para destaque
    public int corTextoPrincipal = Color.parseColor("#FFFFFF"); // Branco puro para leitura rápida
    public int corTextoSecundario = Color.parseColor("#8E92A2");// Cinza azulado para menor contraste

    public CoresTela() {}

    // Construtor auxiliar para facilitar customizações rápidas
    public CoresTela(int corFundo, int corProgresso, int corTextoPrincipal, int corTextoSecundario) {
        this.corFundo = corFundo;
        this.corProgresso = corProgresso;
        this.corTextoPrincipal = corTextoPrincipal;
        this.corTextoSecundario = corTextoSecundario;
    }
}
