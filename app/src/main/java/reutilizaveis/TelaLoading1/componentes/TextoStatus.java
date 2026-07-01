package reutilizaveis.telaloading1.componentes;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import reutilizaveis.telaloading1.configuracao.CoresTela;

public class TextoStatus extends AppCompatTextView {

    public TextoStatus(Context context, CoresTela cores) {
        super(context);
        init(cores);
    }

    private void init(CoresTela cores) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        // Margem superior para afastar da porcentagem/barra
        params.setMargins(0, 24, 0, 0); 
        setLayoutParams(params);

        setGravity(Gravity.CENTER);
        setTextSize(14); // Em SP (escala de texto)
        setTextColor(cores.corTextoSecundario);
        setText("Iniciando...");
    }

    public void setTexto(String status) {
        setText(status);
    }
}
