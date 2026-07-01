package reutilizaveis.telaloading1.componentes;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PainelLogo extends LinearLayout {

    private ImageView logoImageView;

    public PainelLogo(Context context) {
        super(context);
        init();
    }

    private void init() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 32); // Margem inferior para afastar do Card de imagem
        setLayoutParams(params);
        setGravity(Gravity.CENTER);

        logoImageView = new ImageView(getContext());
        // Define um tamanho padrão razoável para a logo (ex: 120dp de largura)
        int tamanhoPx = (int) (120 * getContext().getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(tamanhoPx, LinearLayout.LayoutParams.WRAP_CONTENT);
        logoImageView.setLayoutParams(logoParams);
        logoImageView.setAdjustViewBounds(true); // Mantém a proporção da imagem ao redimensionar

        addView(logoImageView);
    }

    public void setLogoResource(int resId) {
        if (logoImageView != null) {
            logoImageView.setImageResource(resId);
        }
    }
}
