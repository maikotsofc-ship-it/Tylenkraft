package reutilizaveis.menuprincipal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import reutilizaveis.menuprincipal.fundos.FundoParallax3D;
import reutilizaveis.telaloading1.imagens.CarregadorAssets;

public class MenuPrincipal extends FrameLayout {

    private FundoParallax3D fundoParallax;
    private LinearLayout painelBotoes;

    /**
     * EXCELÊNCIA EM ENCAPSULAMENTO:
     * Método estático que injeta o Menu Principal na Activity com apenas uma linha de código.
     */
        /**
     * EXCELÊNCIA EM ENCAPSULAMENTO E CONCORRÊNCIA:
     * Método estático que injeta o Menu Principal garantindo a execução na UI Thread 
     * para evitar crashes de sincronização assíncrona.
     */
    public static void mostrar(@NonNull final Activity activity) {
        // Força a execução na Thread Principal da interface gráfica
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MenuPrincipal menu = new MenuPrincipal(activity);
                ViewGroup containerRaiz = activity.findViewById(android.R.id.content);
                if (containerRaiz != null) {
                    containerRaiz.addView(menu, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }
        });
    }


    // Tornamos o construtor privado para forçar o uso do método estático .mostrar()
    private MenuPrincipal(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 1. Inicializa o fundo 3D Parallax
        fundoParallax = new FundoParallax3D(getContext());
        addView(fundoParallax, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        // Adiciona camadas de teste para o efeito 3D
        fundoParallax.adicionarCamada(CarregadorAssets.carregarBitmap(getContext(), "imagens/loading_banner.png")); 

        // 2. Painel Vertical para os Botões
        painelBotoes = new LinearLayout(getContext());
        painelBotoes.setOrientation(LinearLayout.VERTICAL);
        painelBotoes.setGravity(Gravity.CENTER);
        
        FrameLayout.LayoutParams painelParams = new FrameLayout.LayoutParams(
                (int) (250 * getContext().getResources().getDisplayMetrics().density), 
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        painelParams.gravity = Gravity.CENTER_HORIZONTAL;
        painelBotoes.setLayoutParams(painelParams);

        // 3. Título ou Logo do Jogo
        TextView titulo = new TextView(getContext());
        titulo.setText("TYLENKRAFT");
        titulo.setTextSize(32);
        titulo.setTextColor(Color.WHITE);
        titulo.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tituloParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tituloParams.bottomMargin = 40;
        titulo.setLayoutParams(tituloParams);
        painelBotoes.addView(titulo);

        // 4. Criação dos Botões Solicitados
        criarBotaoMenu("JOGAR", () -> dispararAcao("Iniciando Mundo..."));
        criarBotaoMenu("MODOS DE JOGO", () -> dispararAcao("Abrindo Modos..."));
        criarBotaoMenu("PERSONAGENS", () -> dispararAcao("Customizar Skin..."));
        criarBotaoMenu("LOJA", () -> dispararAcao("Abrindo Mercado..."));
        criarBotaoMenu("CONFIGURAÇÕES", () -> dispararAcao("Configurações..."));

        addView(painelBotoes);
    }

    private void criarBotaoMenu(String texto, final Runnable acao) {
        Button botao = new Button(getContext());
        botao.setText(texto);
        botao.setTextColor(Color.WHITE);
        botao.setBackgroundColor(Color.parseColor("#1A1A1A")); 
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 
                (int) (48 * getContext().getResources().getDisplayMetrics().density)
        );
        params.bottomMargin = 16; 
        botao.setLayoutParams(params);
        
        botao.setOnClickListener(v -> acao.run());
        painelBotoes.addView(botao);
    }

    private void dispararAcao(String mensagem) {
        // CORRIGIDO: Exibe o texto correto da ação clicada no Toast
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (fundoParallax != null) {
            fundoParallax.atualizarDeslocamento(event.getX(), event.getY());
        }
        return true;
    }
}
