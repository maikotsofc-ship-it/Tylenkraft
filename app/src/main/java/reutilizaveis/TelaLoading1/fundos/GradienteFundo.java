package reutilizaveis.telaloading1.fundos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;
import reutilizaveis.telaloading1.configuracao.CoresTela;

public class GradienteFundo extends View {

    private final Paint paint;
    private final CoresTela cores;

    public GradienteFundo(Context context, CoresTela cores) {
        super(context);
        this.cores = cores;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Cria um degradê linear do topo esquerdo para o canto inferior direito
        LinearGradient gradient = new LinearGradient(
                0, 0, getWidth(), getHeight(),
                cores.corFundo, 
                alterarBrilho(cores.corFundo, 0.7f), // Cria um tom levemente mais escuro/diferente para o fim
                Shader.TileMode.CLAMP
        );
        
        paint.setShader(gradient);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    // Auxiliar simples para misturar a cor e gerar profundidade no gradiente nativo
    private int alterarBrilho(int cor, float fator) {
        int a = (cor >> 24) & 0xFF;
        int r = Math.round(((cor >> 16) & 0xFF) * fator);
        int g = Math.round(((cor >> 8) & 0xFF) * fator);
        int b = Math.round((cor & 0xFF) * fator);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
