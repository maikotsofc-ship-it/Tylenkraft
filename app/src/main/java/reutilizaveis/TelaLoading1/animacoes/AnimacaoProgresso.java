package reutilizaveis.telaloading1.animacoes;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import reutilizaveis.telaloading1.componentes.BarraCarregamento;

public class AnimacaoProgresso {

    private ValueAnimator animator;

    /**
     * Desloca o preenchimento da barra de forma suave entre o ponto atual e o novo alvo.
     */
    public void animarPara(BarraCarregamento barra, int novoProgresso, long duracaoMs) {
        if (barra == null) return;

        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }

        int progressoAtual = barra.getProgress();

        // Cria o interpolador que vai do valor atual até o novo alvo
        animator = ValueAnimator.ofInt(progressoAtual, novoProgresso);
        animator.setDuration(duracaoMs);
        
        // DecelerateInterpolator faz a animação começar rápida e desacelerar no final (mais elegante)
        animator.setInterpolator(new DecelerateInterpolator());

        animator.addUpdateListener(animation -> {
            int valorAnimado = (int) animation.getAnimatedValue();
            barra.setProgress(valorAnimado);
        });

        animator.start();
    }
}
