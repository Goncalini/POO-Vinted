import java.io.Serializable;

public class TShirt extends Artigo implements Serializable {

    enum Tamanho{
        S,
        M,
        L,
        XL
    }

    private Tamanho tamanho;

    enum Padrao {
        liso,
        riscas,
        palmeiras
    }

    private Padrao padrao;

    public TShirt(){
        this.tamanho = null;
        this.padrao = null;
    }

    public TShirt(Tamanho tamanho, Padrao padrao) {
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public TShirt(String avaliacaoEstado, int numDonos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, Tamanho tamanho, Padrao padrao) {
        super(avaliacaoEstado, numDonos, descricao, marca, ID, precoBase, idTransportadora, idVendedor);
        this.tamanho = tamanho;
        this.padrao = padrao;
    }

    public TShirt(TShirt tshirt){
        super(tshirt.getAvaliacaoEstado(),tshirt.getNumDonos(),tshirt.getDescricao(),tshirt.getMarca(),tshirt.getID(),tshirt.getPrecoBase(),tshirt.getTransportadora(), tshirt.getIdVendedor());
        this.tamanho = tshirt.getTamanho();
        this.padrao = tshirt.getPadrao();
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public Padrao getPadrao() {
        return padrao;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public void setPadrao(Padrao padrao) {
        this.padrao = padrao;
    }

    public TShirt clone(){
        return new TShirt(this);
    }

    //------------------------------------------------------------------------------------------------------------------

    public double calculaPreco(){
        double precoAtual = this.getPrecoBase();
        if(this.padrao != Padrao.liso && this.getEstado() == Estado.Usado)
            precoAtual *= 0.5;
        return precoAtual;
    }

}
