import java.io.Serializable;

public class Mala extends Artigo implements Serializable {

    private int anoColecao;
    private int tamanho;
    private int estado_material;
    private int idade;

   public Mala(){
       super();
       this.anoColecao = 0;
       this.tamanho = 0;
       this.estado_material = 0;
       this.idade = 0;
   }

    public Mala(String avaliacaoEstado, int num_donos, String descricao, String marca, String ID, double precoBase, String idTransportadora,String idVendedor, int anoColecao, int tamanho, int material) {
        super(avaliacaoEstado, num_donos, descricao, marca, ID, precoBase, idTransportadora,idVendedor);
        this.anoColecao = anoColecao;
        this.tamanho = tamanho;
        if(material > 5) {
            this.estado_material = 5;
        }
        else if(material <= 0){
            this.estado_material = 1;
        }
        else{
            this.estado_material = material;
        }
    }

    public Mala(Mala mala){
       super(mala.getAvaliacaoEstado(),mala.getNumDonos(),mala.getDescricao(),mala.getMarca(),mala.getID(),mala.getPrecoBase(),mala.getTransportadora(), mala.getIdVendedor());
       this.anoColecao = mala.getAnoColecao();
       this.tamanho = mala.getTamanho();
       this.estado_material = mala.getMaterial();
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getAnoColecao() {
        return anoColecao;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getMaterial() {
        return estado_material;
    }

    public void setAnoColecao(int anoColecao) {
        this.anoColecao = anoColecao;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setMaterial(int material) {
        this.estado_material = material;
    }

    public Mala clone(){
        return new Mala(this);
    }

    //------------------------------------------------------------------------------------------------------------------

    public double calculaPreco(){
        double precoAtual = this.getPrecoBase();
        return precoAtual - ((1./this.tamanho) + (1./this.estado_material) + (1./this.anoColecao));
    }

}
