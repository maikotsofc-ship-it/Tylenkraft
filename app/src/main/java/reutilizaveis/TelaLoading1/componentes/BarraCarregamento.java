package reutilizaveis.telaloading1.componentes;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ClipDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import reutilizaveis.telaloading1.configuracao.CoresTela;

public class BarraCarregamento extends ProgressBar {

    public BarraCarregamento(Context context, CoresTela cores) {
        // Estilo nativo para barra horizontal
        super(context, null, android.R.attr.progressBarStyleHorizontal); 
        init(cores);
    }

    private void init(CoresTela cores) {
        // Define largura e altura da barra (ex: altura de 8dp aproximado)
        int alturaPx = (int) (8 * getContext().getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                alturaPx
        );
        // Margens laterais para a barra não bater nas bordas do celular
        int margemLateral = (int) (48 * getContext().getResources().getDisplayMetrics().density);
        params.setMargins(margemLateral, 40, margemLateral, 0);
        setLayoutParams(params);

        setMax(100);
        setProgress(0);

        // Desenha o fundo da barra (Trilho)
        GradientDrawable fundoDrawable = new GradientDrawable();
        fundoDrawable.setColor(cores.corTextoSecundario & 0x22FFFFFF); // Mesma cor com transparência
        fundoDrawable.setCornerRadius(alturaPx / 2f);

        // Desenha o progresso preenchido
        GradientDrawable progressoDrawable = new GradientDrawable();
        progressoDrawable.setColor(cores.corProgresso);
        progressoDrawable.setCornerRadius(alturaPx / 2f);

        // ClipDrawable faz o efeito de corte/preenchimento da esquerda para a direita
        ClipDrawable clipProgresso = new ClipDrawable(progressoDrawable, Gravity.START, ClipDrawable.HORIZONTAL);

        // Une os dois estados no componente
        this.setBackground(fundoDrawable);
        this.setProgressDrawable(clipProgresso);
    }

    public void setProgresso(int porcentagem) {
        // No Android nativo, setProgress já lida bem com threads visuais principais
        setProgress(porcentagem);
    }
}
