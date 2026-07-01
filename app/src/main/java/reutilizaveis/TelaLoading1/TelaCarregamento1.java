package reutilizaveis.telaloading1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import reutilizaveis.telaloading1.configuracao.ConfiguracaoTela;
import reutilizaveis.telaloading1.configuracao.ConstantesTela;
import reutilizaveis.telaloading1.componentes.PainelLogo;
import reutilizaveis.telaloading1.componentes.CardImagem;
import reutilizaveis.telaloading1.componentes.BarraCarregamento;
import reutilizaveis.telaloading1.componentes.TextoStatus;
import reutilizaveis.telaloading1.componentes.TextoPorcentagem;
import reutilizaveis.telaloading1.fundos.GradienteFundo;
import reutilizaveis.telaloading1.fundos.ParticulasFundo;
import reutilizaveis.telaloading1.dicas.GeradorDicas;
import reutilizaveis.telaloading1.animacoes.AnimacaoProgresso;
import reutilizaveis.telaloading1.imagens.GerenciadorImagens;

public class TelaCarregamento1 extends FrameLayout {

    public interface AoFinalizarListener {
        void onFinalizado();
    }

    private ConfiguracaoTela configuracao;
    private List<String> dicasCustomizadas;
    private GeradorDicas motorDicas;
    private GerenciadorImagens gerenciadorImagens;
    private AnimacaoProgresso animacaoProgresso;
    private AoFinalizarListener finalizarListener;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    
    private GradienteFundo visualFundo;
    private ParticulasFundo visualParticulas;
    private PainelLogo painelLogo;
    private CardImagem cardImagem;
    private BarraCarregamento barraProgresso;
    private TextoStatus textoStatus;
    private TextoPorcentagem textoPorcentagem;

        public static TelaCarregamento1 mostrarEIniciar(@NonNull Activity activity, @Nullable ConfiguracaoTela config, @Nullable AoFinalizarListener listener) {
        TelaCarregamento1 viewLoading = new TelaCarregamento1(activity, config);
        viewLoading.finalizarListener = listener;
        
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, 
                WindowManager.LayoutParams.MATCH_PARENT
        );
        activity.addContentView(viewLoading, params);
        
        // CORREÇÃO DEFINITIVA: Usamos o post() para garantir que o comando imersivo execute
        // apenas DEPOIS que o Android terminou de renderizar e encaixar o loading na janela.
        viewLoading.post(new Runnable() {
            @Override
            public void run() {
                utilitarios.TelaCheia.TelaCheia1.ativar(activity);
            }
        });
        
        viewLoading.carregarDicasEIniciarLoops();
        viewLoading.executarFluxoProgresso();
        return viewLoading;
    }

    private TelaCarregamento1(Context context, ConfiguracaoTela config) {
        super(context);
        this.configuracao = (config != null) ? config : new ConfiguracaoTela.Builder().build();
        init();
    }

    private void init() {
        this.gerenciadorImagens = new GerenciadorImagens(getContext());
        this.animacaoProgresso = new AnimacaoProgresso();

        // 1. Backgrounds
        visualFundo = new GradienteFundo(getContext(), configuracao.getCores());
        addView(visualFundo);

        visualParticulas = new ParticulasFundo(getContext(), configuracao.getCores());
        addView(visualParticulas);

        // 2. Estrutura Vertical Principal
        LinearLayout corpoCentral = new LinearLayout(getContext());
        corpoCentral.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams corpoParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        corpoParams.gravity = Gravity.CENTER;
        corpoCentral.setPadding(80, 40, 80, 40); 
        corpoCentral.setLayoutParams(corpoParams);

        // 3. Instancia os componentes visuais
        painelLogo = new PainelLogo(getContext());
        cardImagem = new CardImagem(getContext());
        barraProgresso = new BarraCarregamento(getContext(), configuracao.getCores());
        textoPorcentagem = new TextoPorcentagem(getContext(), configuracao.getCores());
        textoStatus = new TextoStatus(getContext(), configuracao.getCores());

        // Configuração da Logo
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        logoParams.gravity = Gravity.CENTER_HORIZONTAL;
        logoParams.bottomMargin = 50; 
        painelLogo.setLayoutParams(logoParams);

        // Card de imagem ampliado nos parâmetros ideais
        int larguraCard = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.75);
        int alturaCard = (int) (220 * getContext().getResources().getDisplayMetrics().density); 
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(larguraCard, alturaCard);
        cardParams.gravity = Gravity.CENTER_HORIZONTAL;
        cardParams.bottomMargin = 60; 
        cardImagem.setLayoutParams(cardParams);

        try {
            if (gerenciadorImagens != null) {
                gerenciadorImagens.carregarImagemDoAssetNoCard("imagens/loading_banner.png", cardImagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cardImagem.setBackgroundColor(Color.parseColor("#2C2C2C")); 
        }

        // Configuração Padrão da Barra de Carregamento
        LinearLayout.LayoutParams barraParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (12 * getContext().getResources().getDisplayMetrics().density));
        barraParams.bottomMargin = 30;
        barraProgresso.setLayoutParams(barraParams);

        // Organização horizontal inferior (Dicas na Esquerda e Porcentagem na Direita)
        RelativeLayout containerInfeior = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams containerParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        containerInfeior.setLayoutParams(containerParams);

        RelativeLayout.LayoutParams statusParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        statusParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        statusParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textoStatus.setLayoutParams(statusParams);

        RelativeLayout.LayoutParams porcentagemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        porcentagemParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        porcentagemParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textoPorcentagem.setLayoutParams(porcentagemParams);

        containerInfeior.addView(textoStatus);
        containerInfeior.addView(textoPorcentagem);

        // 4. Monta a árvore de visualização
        corpoCentral.addView(painelLogo);
        corpoCentral.addView(cardImagem);
        corpoCentral.addView(barraProgresso);
        corpoCentral.addView(containerInfeior); 

        addView(corpoCentral);

        visualParticulas.iniciarAnimacao();
    }

    private void carregarDicasEIniciarLoops() {
        this.dicasCustomizadas = Arrays.asList(
                "Gerando estruturas no mundo...",
                "Misturando poções e polindo blocks...",
                "Alimentando os lobos selvagens...",
                "Sincronizando chunks com o servidor..."
        );

        if (configuracao.deveExibirDicas() && textoStatus != null) {
            motorDicas = new GeradorDicas(dicasCustomizadas, configuracao.getIntervaloDicasMs());
            motorDicas.iniciarLoop(textoStatus);
        }
    }

    private void executarFluxoProgresso() {
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                atualizarProgresso(40, "Carregando texturas do TylenKraft...");

                Thread.sleep(1500);
                atualizarProgresso(80, "Gerando chunks...");

                Thread.sleep(1000);
                fechar();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void atualizarProgresso(final int porcentagem, final String status) {
        mainHandler.post(() -> {
            if (barraProgresso != null && animacaoProgresso != null) {
                animacaoProgresso.animarPara(barraProgresso, porcentagem, 800); 
            }
            if (textoPorcentagem != null) {
                textoPorcentagem.setTexto(porcentagem + "%");
            }
            if (textoStatus != null && status != null && !configuracao.deveExibirDicas()) {
                textoStatus.setTexto(status);
            }
        });
    }

    public void fechar() {
        mainHandler.post(() -> {
            if (visualParticulas != null) visualParticulas.pararAnimacao();
            if (motorDicas != null) motorDicas.pararLoop();
            
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeView(this);
            }

            if (finalizarListener != null) {
                finalizarListener.onFinalizado();
            }
        });
    }
}
