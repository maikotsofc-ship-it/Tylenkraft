package utilitarios.TelaCheia; // Mantendo o seu pacote global

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TelaCheia1 {

    /**
     * Ativa o modo imersivo total e estica o jogo por toda a tela física do aparelho.
     */
    public static void ativar(Activity activity) {
        if (activity == null) return;

        // 1. REMOVIDO: requestWindowFeature foi retirado para evitar conflito com o Theme do Manifest

        // 2. Garante compatibilidade e força fullscreen em versões anteriores do Android
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // 3. ATIVAÇÃO DO MODO IMERSIVO TOTAL STICKY (O segredo para jogos)
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Esconde barra de navegação
                | View.SYSTEM_UI_FLAG_FULLSCREEN      // Esconde barra de status
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // Mantém oculto mesmo se interagir na tela

        decorView.setSystemUiVisibility(uiOptions);
        
        // 4. Correção para telas modernas com Notch/Furo de Câmera (Display Cutout)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.getWindow().getAttributes().layoutInDisplayCutoutMode = 
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }
}
