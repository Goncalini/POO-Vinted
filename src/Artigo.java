import java.io.Serializable;

public abstract class Artigo implements Serializable{

    public enum Estado{
        Novo, Usado
    }

    private Estado estado; // estado do artigo
    private String avaliacaoEstado; //avaliaçao do estado, caso este seja usado
    private int numDonos; // numero de donos do artigo
    private String descricao; // descriçao do artigo
    private String marca; // marca do artigo
    private String ID; // ID do artigo
    private double precoBase; // preço base do artigo
    private double correcaoPreco; // preço depois de descontos aplicados
    private String idTransportadora; // id da transportadora
    private String idVendedor; // id do dono do artigo
    private String idExVendedor; // id do ex dono do artigo

    public Artigo(){
        this.estado = Estado.Novo;
        this.avaliacaoEstado = "";
        this.numDonos = 0;
        this.descricao = "";
        this.marca = "";
        this.ID = "";
        this.precoBase = 0;
        this.correcaoPreco = 0;
        this.idTransportadora = "";
        this.idVendedor = "";
        this.idExVendedor = "";
    }

    public Artigo(String avaliacaoEstado, int numDonos, String descricao, String marca, String ID, double precoBase, String idTransportadora, String idVendedor) {
        this.numDonos = numDonos;
        this.descricao = descricao;
        this.marca = marca;
        this.ID = ID;
        this.precoBase = precoBase;
        this.idTransportadora = idTransportadora;
        this.idVendedor = idVendedor;
        this.idExVendedor = "";
        if(numDonos == 0){
            this.estado = Estado.Novo;
            this.avaliacaoEstado = "";
        }
        else {
            this.estado = Estado.Usado;
            this.avaliacaoEstado = avaliacaoEstado;
        }
    }

    public Artigo(Artigo artigo){
        this.estado = artigo.getEstado();
        this.avaliacaoEstado = artigo.getAvaliacaoEstado();
        this.numDonos = artigo.getNumDonos();
        this.descricao = artigo.getDescricao();
        this.marca = artigo.getMarca();
        this.ID = artigo.getID();
        this.precoBase = artigo.getPrecoBase();
        this.correcaoPreco = artigo.getCorrecaoPreco();
        this.idTransportadora = artigo.getTransportadora();
        this.idVendedor = artigo.getIdVendedor();
        this.idExVendedor = artigo.getIdExVendedor();
    }

    @Override
    public String toString() {
        return "Artigo{" +
                "estado=" + estado +
                ", avaliaçaoEstado='" + avaliacaoEstado + '\'' +
                ", numDonos=" + numDonos +
                ", descricao='" + descricao + '\'' +
                ", marca='" + marca + '\'' +
                ", ID='" + ID + '\'' +
                ", precoBase=" + precoBase +
                ", idTransportadora=" + idTransportadora +
                '}';
    }

    public String getIdExVendedor() {
        return idExVendedor;
    }

    public void setIdExVendedor(String idExVendedor) {
        this.idExVendedor = idExVendedor;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getAvaliacaoEstado() {
        return avaliacaoEstado;
    }

    public int getNumDonos() {
        return numDonos;
    }

    public Estado getEstado() {
        return estado;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public String getID() {
        return ID;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public String getTransportadora() {
        return idTransportadora;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setAvaliacaoEstado(String avaliacaoEstado) {
        this.avaliacaoEstado = avaliacaoEstado;
    }

    public double getCorrecaoPreco() {
        return correcaoPreco;
    }

    public void setCorrecaoPreco(double correcaoPreco) {
        this.correcaoPreco = correcaoPreco;
    }

    public void setNumDonos(int numDonos) {
        this.numDonos = numDonos;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public void setTransportadora(String idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    public abstract Artigo clone();

    //------------------------------------------------------------------------------------------------------------------

    public abstract double calculaPreco();

    public double precoExpedicao(int opcao, double margemLucro, double imposto){
        switch(opcao) {
            case 1 -> {
                return Formulas.formula1(this.precoBase, margemLucro, imposto);
            }
            case 2 -> {
                return Formulas.formula2(this.precoBase, margemLucro, imposto);
            }
            default -> {
                return Formulas.formula3(this.precoBase, margemLucro, imposto);
            }
        }
    }

    public double lucroTransportadoraPorArtigo(int opcao,double margemLucro, double imposto){
        return ((this.precoExpedicao(opcao,margemLucro,imposto)) - this.calculaPreco());
    }

}
