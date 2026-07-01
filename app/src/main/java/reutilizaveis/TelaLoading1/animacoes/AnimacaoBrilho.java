package reutilizaveis.telaloading1.animacoes;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimacaoBrilho {

    private ObjectAnimator animator;

    /**
     * Inicia o efeito de pulsação infinita de opacidade na View selecionada.
     */
    public void iniciar(View view, long duracaoCicloMs) {
        if (view == null) return;
        
        // Para se já houver uma animação rodando nesta instância
        parar();

        // Anima a propriedade "alpha" (transparência) de 100% para 40%
        animator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.4f);
        animator.setDuration(duracaoCicloMs);
        
        // Define que ao chegar no final ele volta (efeito vai e vem) de forma infinita
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        
        animator.start();
    }

    /**
     * Interrompe a animação com segurança e restaura o estado original da View.
     */
    public void parar() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
    }
}
