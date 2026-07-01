package reutilizaveis.telaloading1.util;

public class UtilTexto {

    /**
     * Garante que o texto não passe de um limite, cortando-o e adicionando "..." se necessário.
     */
    public static String limitarTexto(String texto, int maxCaracteres) {
        if (texto == null || texto.length() <= maxCaracteres) {
            return texto;
        }
        return texto.substring(0, maxCaracteres - 3) + "...";
    }
}
