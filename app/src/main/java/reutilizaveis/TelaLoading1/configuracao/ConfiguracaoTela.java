package reutilizaveis.telaloading1.configuracao;

public class ConfiguracaoTela {
    private final CoresTela cores;
    private final boolean exibirDicas;
    private final int intervaloDicasMs;

    private ConfiguracaoTela(Builder builder) {
        this.cores = builder.cores;
        this.exibirDicas = builder.exibirDicas;
        this.intervaloDicasMs = builder.intervaloDicasMs;
    }

    // Getters para a TelaCarregamento1 consultar as preferências
    public CoresTela getCores() { return cores; }
    public boolean deveExibirDicas() { return exibirDicas; }
    public int getIntervaloDicasMs() { return intervaloDicasMs; }

    /**
     * Builder estático para construção flexível do objeto de configuração
     */
    public static class Builder {
        private CoresTela cores = new CoresTela();
        private boolean exibirDicas = true;
        private int intervaloDicasMs = ConstantesTela.INTERVALO_DICAS_PADRAO_MS;

        public Builder comCores(CoresTela cores) {
            this.cores = cores;
            return this;
        }

        public Builder ocultarDicas() {
            this.exibirDicas = false;
            return this;
        }

        public Builder alterarIntervaloDicas(int ms) {
            this.intervaloDicasMs = ms;
            return this;
        }

        public ConfiguracaoTela build() {
            return new ConfiguracaoTela(this);
        }
    }
}
