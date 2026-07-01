package reutilizaveis.telaloading1.imagens;

import android.graphics.Bitmap;
import android.util.LruCache;

public class CacheImagens {

    private static CacheImagens instancia;
    private LruCache<String, Bitmap> memoriaCache;

    private CacheImagens() {
        init();
    }

    public static synchronized CacheImagens getInstancia() {
        if (instancia == null) {
            instancia = new CacheImagens();
        }
        return instancia;
    }

    private void init() {
        // Calcula a memória máxima disponível e reserva uma parte para o cache (ex: 1/8)
        final int maxMemoriaKilobytes = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int tamanhoCacheKilobytes = maxMemoriaKilobytes / 8;

        memoriaCache = new LruCache<String, Bitmap>(tamanhoCacheKilobytes) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // Retorna o tamanho do bitmap em kilobytes
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void adicionarBitmap(String chave, Bitmap bitmap) {
        if (getBitmap(chave) == null && bitmap != null) {
            memoriaCache.put(chave, bitmap);
        }
    }

    public Bitmap getBitmap(String chave) {
        if (chave == null) return null;
        return memoriaCache.get(chave);
    }

    public void limparCache() {
        if (memoriaCache != null) {
            memoriaCache.evictAll();
        }
    }
}
