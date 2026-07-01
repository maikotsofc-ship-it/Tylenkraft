package reutilizaveis.telaloading1.imagens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;

public class CarregadorAssets {

    public static Bitmap carregarBitmap(Context context, String caminhoArquivo) {
        InputStream is = null;
        try {
            is = context.getAssets().open(caminhoArquivo);
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try { is.close(); } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
}
