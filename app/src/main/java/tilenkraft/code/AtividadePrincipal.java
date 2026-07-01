package tylenkraft.code;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import reutilizaveis.telaloading1.TelaCarregamento1;
import reutilizaveis.menuprincipal.MenuPrincipal;
import utilitarios.TelaCheia.TelaCheia1;

public class AtividadePrincipal extends Activity {

    private TelaTeste telaTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Prepara as propriedades iniciais da janela
        TelaCheia1.ativar(this);

        // 2. Define o container inicial limpo
        setContentView(new android.widget.FrameLayout(this));

        // 3. Inicia a tela de carregamento (que agora se auto-ajusta em tela cheia internamente)
        TelaCarregamento1.mostrarEIniciar(this, null, new TelaCarregamento1.AoFinalizarListener() {
            @Override
            public void onFinalizado() {
                // O carregamento chegou a 100%!
                
                // A) Instancia e define a view base (SurfaceView) do jogo
                telaTeste = new TelaTeste(AtividadePrincipal.this);
                setContentView(telaTeste);
                
                // CRÍTICO: Reativa a tela cheia após aplicar o layout do jogo
                TelaCheia1.ativar(AtividadePrincipal.this);

                // B) Injeta o Menu Principal por cima da view do jogo
                MenuPrincipal.mostrar(AtividadePrincipal.this);
                
                // CRÍTICO: Garante o travamento completo após o menu estar visível
                TelaCheia1.ativar(AtividadePrincipal.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Garante que se o jogador sair do app e voltar, o jogo volte em tela cheia
        TelaCheia1.ativar(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // Toda vez que a tela ganhar foco (ao iniciar o loading, ao abrir o menu, ou interagir),
        // o modo imersivo STICKY será blindado contra barras invasoras do sistema.
        if (hasFocus) {
            TelaCheia1.ativar(this);
        }
    }

    // --- A classe interna TelaTeste (SurfaceView) permanece intacta ---
    public static class TelaTeste extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback, Runnable {
        public TelaTeste(android.content.Context context) { super(context); }
        @Override public void surfaceCreated(android.view.SurfaceHolder h) {}
        @Override public void surfaceChanged(android.view.SurfaceHolder h, int f, int w, int h2) {}
        @Override public void surfaceDestroyed(android.view.SurfaceHolder h) {}
        @Override public void run() {}
    }
}
