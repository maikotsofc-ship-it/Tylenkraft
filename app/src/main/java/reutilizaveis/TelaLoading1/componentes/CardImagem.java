package reutilizaveis.telaloading1.componentes;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CardImagem extends FrameLayout {

    private ImageView imageViewInterna;

    public CardImagem(Context context) {
        super(context);
        init();
    }

    private void init() {
        // Define o tamanho do Card no centro da tela (ex: 280dp x 180dp)
        float densidade = getContext().getResources().getDisplayMetrics().density;
        int larguraPx = (int) (280 * densidade);
        int alturaPx = (int) (180 * densidade);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(larguraPx, alturaPx);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        setLayoutParams(params);

        // Arredondamento dos cantos (16dp)
        final int raioCurva = (int) (16 * densidade);

        // Cria o fundo do card para estruturar a forma
        GradientDrawable fundoCard = new GradientDrawable();
        fundoCard.setColor(0x11FFFFFF); // Branco ultra leve/translúcido de base
        fundoCard.setCornerRadius(raioCurva);
        setBackground(fundoCard);

        // Configura o ImageView interno
        imageViewInterna = new ImageView(getContext());
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        imageViewInterna.setLayoutParams(imgParams);
        imageViewInterna.setScaleType(ImageView.ScaleType.CENTER_CROP); // Corta proporcionalmente para preencher

        // TRUQUE PRECIOSO: Força o Android a cortar a imagem interna no formato arredondado do card
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), raioCurva);
            }
        });
        setClipToOutline(true);

        addView(imageViewInterna);
    }

    // Método para aplicar bitmaps gerados ou cacheados dinamicamente
    public void setImagem(Bitmap bitmap) {
        if (imageViewInterna != null) {
            imageViewInterna.setImageBitmap(bitmap);
        }
    }

    // Método alternativo para testar com drawables locais
    public void setImagemResource(int resId) {
        if (imageViewInterna != null) {
            imageViewInterna.setImageResource(resId);
        }
    }
}
