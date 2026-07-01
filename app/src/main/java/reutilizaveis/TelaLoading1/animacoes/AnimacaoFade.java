package reutilizaveis.telaloading1.animacoes;

import android.view.View;

public class AnimacaoFade {

    /**
     * Faz uma View aparecer suavemente alterando o Alpha de 0 para 1.
     */
    public static void entrar(View view, long duracaoMs) {
        if (view == null) return;
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(duracaoMs)
                .setListener(null); // Limpa listeners antigos
    }

    /**
     * Faz uma View sumir suavemente e, ao final, altera a visibilidade para GONE.
     */
    public static void sair(View view, long duracaoMs, Runnable aoFinalizar) {
        if (view == null) return;
        view.animate()
                .alpha(0f)
                .setDuration(duracaoMs)
                .withEndAction(() -> {
                    view.setVisibility(View.GONE);
                    if (aoFinalizar != null) {
                        aoFinalizar.run();
                    }
                });
    }
}
