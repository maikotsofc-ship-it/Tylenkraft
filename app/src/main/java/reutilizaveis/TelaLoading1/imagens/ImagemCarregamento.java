package reutilizaveis.telaloading1.imagens;

import android.graphics.Bitmap;

public class ImagemCarregamento {
    private final String id;
    private final Bitmap bitmap;

    public ImagemCarregamento(String id, Bitmap bitmap) {
        this.id = id;
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
