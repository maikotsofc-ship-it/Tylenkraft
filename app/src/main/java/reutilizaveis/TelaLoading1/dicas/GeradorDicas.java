package reutilizaveis.telaloading1.dicas;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import reutilizaveis.telaloading1.animacoes.AnimacaoFade;

public class GeradorDicas {

    private final List<Dica> listaDicas = new ArrayList<>();
    private final Random random = new Random();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnableLoop;
    
    private int intervaloMs;
    private int indiceAtual = -1;

    public GeradorDicas(List<String> frases, int intervaloMs) {
        this.intervaloMs = intervaloMs;
        if (frases != null && !frases.isEmpty()) {
            for (String frase : frases) {
                listaDicas.add(new Dica(frase));
            }
        } else {
            // Dicas padrão de contingência caso o desenvolvedor não envie nenhuma
            listaDicas.add(new Dica("A carregar os recursos do sistema..."));
            listaDicas.add(new Dica("Quase pronto! A preparar a sua experiência..."));
            listaDicas.add(new Dica("A otimizar as definições de desempenho..."));
        }
    }

    /**
     * Inicia o ciclo de rotação automática de dicas com transição suave (Fade).
     */
    public void iniciarLoop(final TextView textViewTarget) {
        if (textViewTarget == null || listaDicas.isEmpty()) return;

        // Para qualquer loop que porventura já estivesse a rodar nesta instância
        pararLoop();

        runnableLoop = new Runnable() {
            @Override
            public void run() {
                // Escolhe uma dica diferente da atual para não repetir seguidamente
                int proximoIndice;
                if (listaDicas.size() > 1) {
                    do {
                        proximoIndice = random.nextInt(listaDicas.size());
                    } while (proximoIndice == indiceAtual);
                } else {
                    proximoIndice = 0;
                }
                
                indiceAtual = proximoIndice;
                final Dica dicaSelecionada = listaDicas.get(indiceAtual);

                // Aplica o efeito Fade Out (Desaparecer) -> Troca o texto -> Fade In (Aparecer)
                AnimacaoFade.sair(textViewTarget, 300, () -> {
                    textViewTarget.setText(dicaSelecionada.getTexto());
                    AnimacaoFade.entrar(textViewTarget, 300);
                });

                // Agenda a próxima execução do loop
                handler.postDelayed(this, intervaloMs);
            }
        };

        // Executa imediatamente a primeira dica
        handler.post(runnableLoop);
    }

    /**
     * Interrompe o Handler imediatamente. Crucial para o ciclo de vida do Android.
     */
    public void pararLoop() {
        if (handler != null && runnableLoop != null) {
            handler.removeCallbacks(runnableLoop);
        }
    }
}
