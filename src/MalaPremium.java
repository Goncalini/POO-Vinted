import java.io.Serializable;

public class MalaPremium extends Mala implements Serializable {

    private int valorizacao;

    public MalaPremium(){
        super();
        this.valorizacao = 0;
    }

    public MalaPremium(Mala mala, int valorizacao){
        super(mala);
        this.valorizacao = valorizacao;
    }

    public MalaPremium(String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int anoColecao, int tamanho, int material, int valorizacao) {
        super(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora, idVendedor, anoColecao, tamanho, material);
        this.valorizacao = valorizacao;
    }

    public MalaPremium(MalaPremium mala){
        super(mala.getAvaliacaoEstado(),mala.getNumDonos(),mala.getDescricao(),mala.getMarca(),mala.getID(),mala.getPrecoBase(),mala.getTransportadora(), mala.getIdVendedor(), mala.getAnoColecao(),mala.getTamanho(),mala.getMaterial());
        this.valorizacao = mala.getValorizacao();
    }

    public int getValorizacao() {
        return valorizacao;
    }

    public void setValorizacao(int valorizacao) {
        this.valorizacao = valorizacao;
    }

    public MalaPremium clone(){
        return new MalaPremium(this);
    }

    //------------------------------------------------------------------------------------------------------------------

    public double calculaPreco() {
        double precoAtual = this.getPrecoBase();
        precoAtual += (precoAtual*(valorizacao /100.)*this.getIdade());
        return precoAtual;
    }

}

