import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Encomenda implements Serializable {

    private String ID; // id da encomenda
    private Map<String,Artigo> lista_artigos; // coleçao de artigos da encomenda

    enum Dimensao {
        pequeno,
        medio,
        grande
    }

    private Dimensao dimensao; //dimensao da encomenda
    private double preco_final; //preço final da encomenda
    private double custos_de_expedicao; // custo de expediçao da encomenda, dependendo de cada transportadora

    enum Estado {
        pendente,
        finalizada,
        expedida
    }

    private Estado estado; //estado da encomenda (começa como pendente, passa a expedida depois de ser feita e passa a finalizada depois de 2 dias da criaçao da encomenda
    private LocalDate data_de_criacao;

    public Encomenda() {
        this.lista_artigos = new HashMap<>();
        this.dimensao = Dimensao.pequeno;
        this.preco_final = 0;
        this.custos_de_expedicao = 0;
        this.estado = Estado.pendente;
        this.data_de_criacao = LocalDate.now();
    }

    public Encomenda(String ID, Map<String,Artigo> lista_artigos) {
            this.setID(ID);
            this.setListaArtigos(lista_artigos);
            this.estado = Estado.pendente;
    }

    public Encomenda(Encomenda encomenda) {
        this.ID = encomenda.getID();
        this.lista_artigos = encomenda.getListaArtigos();
        this.dimensao = encomenda.getDimensao();
        this.preco_final = encomenda.getPrecoFinal();
        this.custos_de_expedicao = encomenda.getCustosDeExpedicao();
        this.estado = encomenda.getEstado();
        this.data_de_criacao = encomenda.getDataDeCriacao();
    }

    public Map<String,Artigo> getListaArtigos() {
        Map<String,Artigo> newmap = new HashMap<String,Artigo>();
        for(Map.Entry<String,Artigo> m : lista_artigos.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }

    public double getPrecoFinal() {
        return preco_final;
    }

    public double getCustosDeExpedicao() {
        return custos_de_expedicao;
    }

    public Estado getEstado() {
        return estado;
    }

    public LocalDate getDataDeCriacao() {
        return data_de_criacao;
    }

    public void setListaArtigos(Map<String,Artigo> listaartigos) {
        this.lista_artigos = new HashMap<>();
        for(String l : listaartigos.keySet())
            this.lista_artigos.put(l,listaartigos.get(l).clone());
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }

    public void setPrecoFinal(double precoFinal) {
        this.preco_final = precoFinal;
    }

    public void setCustosDeExpedicao(double custosDeExpedicao) {
        this.custos_de_expedicao = custosDeExpedicao;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setDataDeCriacao(LocalDate dataDeCriacao) {
        this.data_de_criacao = dataDeCriacao;
    }

    public Encomenda clone() {
        return new Encomenda(this);
    }

    @Override
    public String toString() {
        return "Encomenda{" +
                "ID='" + ID + '\'' +
                ", lista_artigos=" + lista_artigos +
                ", dimensao=" + dimensao +
                ", preco_final=" + preco_final +
                ", custos_de_expedicao=" + custos_de_expedicao +
                ", estado=" + estado +
                ", data_de_criacao=" + data_de_criacao +
                '}';
    }

    //------------------------------------------------------------------------------------------------------------------

    public void adicionaArtigo(Artigo artigo){
        this.lista_artigos.put(artigo.getID(),artigo.clone());
    }

    public void removeArtigos(){
        this.lista_artigos.clear();
    }

    public void removeArtigo(String id){
        this.lista_artigos.remove(id);
    }

    public double valorFinalEncomenda(Map<String,Transportadora> mapa) {
        double precoTotal = 0;
        for(Artigo artigo : lista_artigos.values()){
            String ID = artigo.getTransportadora();
            Transportadora transportadora = mapa.get(ID);
            if(artigo.getEstado() == Artigo.Estado.Novo)
                precoTotal += artigo.precoExpedicao(transportadora.getFormula(),transportadora.getMargemLucro(),transportadora.getImposto()) + 0.5 + artigo.getCorrecaoPreco();
            else
                precoTotal += artigo.precoExpedicao(transportadora.getFormula(),transportadora.getMargemLucro(),transportadora.getImposto()) + 0.25 + artigo.getCorrecaoPreco();
        }
        return precoTotal;
    }

    public double precoExpedicaoEncomenda(Map<String,Transportadora> mapa){
        double precoExpedicao = 0;
        for(Artigo artigo : lista_artigos.values()) {
            String ID = artigo.getTransportadora();
            Transportadora transportadora = mapa.get(ID);
            precoExpedicao += artigo.precoExpedicao(transportadora.getFormula(),transportadora.getMargemLucro(),transportadora.getImposto());
        }
        return precoExpedicao;
    }

    public double valorVintage(){
        double precoTotal = 0;
        for(Artigo artigo : lista_artigos.values()){
            if(artigo.getEstado() == Artigo.Estado.Novo)
                precoTotal += 0.5;
            else
                precoTotal += 0.25;
        }
        return precoTotal;
    }

    public Dimensao dimensao(){
        int size = this.lista_artigos.size();
        if(size == 1)
            this.dimensao = Dimensao.pequeno;
        else if(size > 1 && size < 6)
            this.dimensao = Dimensao.medio;
        else this.dimensao = Dimensao.grande;
        return this.dimensao;
    }

    public void faturaEncomenda(Map<String,Transportadora> mapa){
        for (Map.Entry<String, Artigo> entry : this.getListaArtigos().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue().toString());
        }
        System.out.println("Preço de expedição: " + this.precoExpedicaoEncomenda(mapa));
        System.out.println("Preço final da encomeda: " + this.valorFinalEncomenda(mapa));
        System.out.println("Preço Vintage: " + this.valorVintage());
        System.out.println("Estado da encomenda: " + this.getEstado());
    }

    public int idadeEncomenda(LocalDate data){
        return data.getDayOfYear() - this.data_de_criacao.getDayOfYear();
    }

    public void encomendaFinalizada(LocalDate data){
        if(this.idadeEncomenda(data) >= 2)
            this.setEstado(Estado.expedida);
    }

}
