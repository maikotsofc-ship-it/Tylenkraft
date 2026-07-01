package reutilizaveis.telaloading1.fundos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import reutilizaveis.telaloading1.configuracao.CoresTela;

public class ParticulasFundo extends View {

    private final Paint paint;
    private final List<Estrela> particulas = new ArrayList<>();
    private final int MAX_PARTICULAS = 40; // Quantidade ideal para beleza e performance
    private final Random random = new Random();
    private boolean animando = false;
    private final CoresTela cores;

    public ParticulasFundo(Context context, CoresTela cores) {
        super(context);
        this.cores = cores;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void iniciarAnimacao() {
        animando = true;
        invalidate(); // Força o Android a chamar o onDraw() pela primeira vez
    }

    public void pararAnimacao() {
        animando = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Inicializa as partículas na primeira execução, quando já sabemos o tamanho real da tela
        if (particulas.isEmpty() && getWidth() > 0) {
            for (int i = 0; i < MAX_PARTICULAS; i++) {
                particulas.add(new Estrela(getWidth(), getHeight(), random));
            }
        }

        // Cor do desenho baseada na cor do progresso, criando harmonia visual
        paint.setColor(cores.corProgresso);

        // Desenha e atualiza cada partícula
        for (Estrela estrela : particulas) {
            paint.setAlpha(estrela.alpha);
            canvas.drawCircle(estrela.x, estrela.y, estrela.tamanho, paint);
            estrela.atualizar(getHeight());
        }

        // Ciclo infinito do Loop de Animação nativo
        if (animando) {
            postInvalidateOnAnimation(); // Re-executa o desenho no próximo frame do display
        }
    }
}
