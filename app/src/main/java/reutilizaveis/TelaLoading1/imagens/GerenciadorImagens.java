package reutilizaveis.telaloading1.imagens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import reutilizaveis.telaloading1.componentes.CardImagem;

public class GerenciadorImagens {

    private final Context context;
    private final CacheImagens cache;
    private final ExecutorService executor; 
    private final Handler mainHandler; 

    public GerenciadorImagens(Context context) {
        this.context = context.getApplicationContext(); 
        this.cache = CacheImagens.getInstancia();
        this.executor = Executors.newSingleThreadExecutor(); 
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Carrega uma imagem da pasta /assets (ex: "imagens/preview.png") 
     * diretamente no card fornecido, usando a Thread separada e o Cache de memória.
     */
    public void carregarImagemDoAssetNoCard(final String caminhoAsset, final CardImagem card) {
        if (card == null || caminhoAsset == null) return;

        // 1. Tenta buscar no cache imediatamente
        Bitmap bitmapCached = cache.getBitmap(caminhoAsset);
        if (bitmapCached != null) {
            card.setImagem(bitmapCached);
            return;
        }

        // 2. Se não estiver em cache, carrega em background de forma assíncrona
        executor.execute(() -> {
            InputStream is = null;
            try {
                // CORRIGIDO AQUI: Nome da variável consertado para 'caminhoAsset'
                is = context.getAssets().open(caminhoAsset);
                final Bitmap bitmapCarregado = BitmapFactory.decodeStream(is);
                
                if (bitmapCarregado != null) {
                    // Guarda no LruCache
                    cache.adicionarBitmap(caminhoAsset, bitmapCarregado);

                    // Devolve o bitmap para a UI Thread exibir
                    mainHandler.post(() -> card.setImagem(bitmapCarregado));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Mantém o carregamento tradicional por ID (Drawable).
     */
    public void carregarImagemNoCard(final int resId, final CardImagem card) {
        if (card == null) return;
        final String chave = String.valueOf(resId);

        Bitmap bitmapCached = cache.getBitmap(chave);
        if (bitmapCached != null) {
            card.setImagem(bitmapCached);
            return;
        }

        executor.execute(() -> {
            try {
                final Bitmap bitmapCarregado = BitmapFactory.decodeResource(context.getResources(), resId);
                if (bitmapCarregado != null) {
                    cache.adicionarBitmap(chave, bitmapCarregado);
                    mainHandler.post(() -> card.setImagem(bitmapCarregado));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
