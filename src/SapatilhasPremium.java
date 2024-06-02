import java.io.Serializable;
import java.time.LocalDate;
public class SapatilhasPremium extends Sapatilhas implements Serializable {

    private String autor;

    public SapatilhasPremium() {
        this.autor = "";
    }

    public SapatilhasPremium(Sapatilhas sapatilhas, String autor) {
        super(sapatilhas);
        this.autor = autor;
    }

    public SapatilhasPremium(String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor, int tamanho, boolean atacadores, String cor, LocalDate dataDeLancamento, String autor) {
        super(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora, idVendedor, tamanho, atacadores, cor, dataDeLancamento);
        this.autor = autor;
    }

    public SapatilhasPremium(SapatilhasPremium sapatilhas) {
        super(sapatilhas.getAvaliacaoEstado(), sapatilhas.getNumDonos(), sapatilhas.getDescricao(), sapatilhas.getMarca(), sapatilhas.getID(), sapatilhas.getPrecoBase(), sapatilhas.getTransportadora(), sapatilhas.getIdVendedor(), sapatilhas.getTamanho(), sapatilhas.getAtacadores(), sapatilhas.getCor(), sapatilhas.getDataDeLancamento());
        this.autor = sapatilhas.getAutor();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public SapatilhasPremium clone() {
        return new SapatilhasPremium(this);
    }

    //------------------------------------------------------------------------------------------------------------------

    public double calculaPreco(){
        double precoAtual = this.getPrecoBase();
        precoAtual += 5*(this.getIdade());
        return precoAtual;
    }

}
