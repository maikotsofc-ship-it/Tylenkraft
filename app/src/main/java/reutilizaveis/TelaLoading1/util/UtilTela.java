package reutilizaveis.telaloading1.util;

import android.content.Context;

public class UtilTela {

    /**
     * Converte um valor em DP para Pixels baseando-se na densidade da tela do dispositivo.
     */
    public static int dpParaPx(Context context, float dp) {
        if (context == null) return (int) dp;
        float densidade = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * densidade);
    }
}
