package reutilizaveis.telaloading1.componentes;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import reutilizaveis.telaloading1.configuracao.CoresTela;

public class TextoPorcentagem extends AppCompatTextView {

    public TextoPorcentagem(Context context, CoresTela cores) {
        super(context);
        init(cores);
    }

    private void init(CoresTela cores) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 32, 0, 0);
        setLayoutParams(params);

        setGravity(Gravity.CENTER);
        setTextSize(28); // Destaque maior
        setTextColor(cores.corTextoPrincipal);
        setTypeface(getTypeface(), android.graphics.Typeface.BOLD);
        setText("0%");
    }

    public void setTexto(String porcentagem) {
        setText(porcentagem);
    }
}
