package reutilizaveis.telaloading1.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

public class UtilAnimacao {

    /**
     * Cria um listener simples que executa uma ação apenas quando a animação chega ao fim.
     */
    public static Animator.AnimatorListener aoFinalizar(final Runnable acao) {
        return new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (acao != null) {
                    acao.run();
                }
            }
        };
    }
}
