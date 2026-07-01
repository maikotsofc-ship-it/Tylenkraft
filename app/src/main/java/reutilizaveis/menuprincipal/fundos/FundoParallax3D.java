package reutilizaveis.menuprincipal.fundos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import reutilizaveis.telaloading1.imagens.CacheImagens;
import reutilizaveis.telaloading1.imagens.GerenciadorImagens;

public class FundoParallax3D extends View {

    private final List<Bitmap> camadas = new ArrayList<>();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    // Deslocamento controlado pelo toque ou sensores para o efeito 3D
    private float offsetX = 0;
    private float offsetY = 0;
    private float targetOffsetX = 0;
    private float targetOffsetY = 0;

    public FundoParallax3D(Context context) {
        super(context);
        init();
    }

    private void init() {
        // Carrega as camadas de fundo usando o gerenciador que já criamos
        GerenciadorImagens gerenciador = new GerenciadorImagens(getContext());
        
        // Exemplo: Você pode colocar 3 imagens no assets (fundo_longe.png, fundo_meio.png, fundo_perto.png)
        // Por enquanto, podemos usar o mesmo banner ou imagens que você adicionar depois
        // gerenciador.carregarImagemDoAssetNoCard("imagens/menu_fundo_1.png", ...);
    }

    public void adicionarCamada(Bitmap bitmap) {
        if (bitmap != null) {
            camadas.add(bitmap);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (camadas.isEmpty()) return;

        // Suaviza o movimento (Interpolação)
        offsetX += (targetOffsetX - offsetX) * 0.1f;
        offsetY += (targetOffsetY - offsetY) * 0.1f;

        // Desenha cada camada aplicando um peso multiplicador diferente (efeito 3D)
        for (int i = 0; i < camadas.size(); i++) {
            Bitmap camada = camadas.get(i);
            
            // Quanto maior o índice 'i', mais perto a camada está, logo, move-se mais rápido!
            float intensidade3D = (i + 1) * 0.15f; 
            
            float x = (getWidth() - camada.getWidth()) / 2f + (offsetX * intensidade3D);
            float y = (getHeight() - camada.getHeight()) / 2f + (offsetY * intensidade3D);

            canvas.drawBitmap(camada, x, y, paint);
        }
        
        // Mantém a animação fluida rodando a 60 FPS
        invalidate();
    }

    /**
     * Move o fundo baseado no arrastar do dedo (pode ser adaptado para o giroscópio depois!)
     */
    public void atualizarDeslocamento(float toqueX, float toqueY) {
        // Limita o deslocamento máximo para a imagem não sair das bordas
        this.targetOffsetX = (toqueX - (getWidth() / 2f)) * 0.05f;
        this.targetOffsetY = (toqueY - (getHeight() / 2f)) * 0.05f;
    }
}
