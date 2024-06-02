import exceptions.IdEncomendaNaoExistenteException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Utilizador implements Serializable {

    private String codigo; // codigo do utilizador
    private String email; //email do utilizador
    private String nome;
    private String morada;
    private int numeroFiscal;
    private Map<String,Artigo> produtosVendidos; //coleçao de produtos vendidos pelo utilizador
    private Map<String,Artigo> produtosParaVenda; //coleçao de produtos que pode vender
    private Map<String,Artigo> produtosAdquiridos; //coleçao de produtos comprados
    private Map<String,Artigo> carrinho; //coleçao com os produtos do carrinho
    private Map<String,Encomenda> comprasFeitas; // coleçao de encomendas feitas
    private double valorVendas; // valor total das vendas feitas

    public static String letra = "A";
    public static long contador = 1;

    public String criaCodigo(){
        String novoCodigo = letra + contador;
        contador++;
        return novoCodigo;
    }

    public Utilizador(){
        this.codigo = "";
        this.email = "";
        this.nome = "";
        this.morada = "";
        this.numeroFiscal = 0;
        this.produtosVendidos = new HashMap<>();
        this.produtosParaVenda = new HashMap<>();
        this.produtosAdquiridos = new HashMap<>();
        this.carrinho = new HashMap<>();
        this.comprasFeitas = new HashMap<>();
        this.valorVendas = 0;
    }

    public Utilizador(String email, String nome, String morada, int numeroFiscal, Map<String,Artigo> produtosVendidos, Map<String,Artigo> produtosParaVenda, Map<String,Artigo> produtosAdquiridos, Map<String,Artigo> carrinho, Map<String,Encomenda> comprasFeitas) {
        this.setCodigo(criaCodigo());
        this.setEmail(email);
        this.setNome(nome);
        this.setMorada(morada);
        this.setNumeroFiscal(numeroFiscal);
        this.setProdutosVendidos(produtosVendidos);
        this.setProdutosParaVenda(produtosParaVenda);
        this.setProdutosAdquiridos(produtosAdquiridos);
        this.setComprasFeitas(comprasFeitas);
        this.setCarrinho(carrinho);
    }

    public Utilizador(Utilizador utilizador){
        this.codigo = utilizador.getCodigo();
        this.email = utilizador.getEmail();
        this.nome = utilizador.getNome();
        this.morada = utilizador.getMorada();
        this.numeroFiscal = utilizador.getNumeroFiscal();
        this.produtosVendidos = utilizador.getProdutosVendidos();
        this.produtosParaVenda = utilizador.getProdutosParaVenda();
        this.produtosAdquiridos = utilizador.getProdutosAdquiridos();
        this.carrinho = utilizador.getCarrinho();
        this.comprasFeitas = utilizador.getComprasFeitas();
        this.valorVendas = utilizador.getValorVendas();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getMorada() {
        return morada;
    }

    public int getNumeroFiscal() {
        return numeroFiscal;
    }
    public Map<String,Artigo> getProdutosVendidos() {
        Map<String,Artigo> newmap = new HashMap<String,Artigo>();
        for(Map.Entry<String,Artigo> m : produtosVendidos.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    public Map<String,Artigo> getProdutosParaVenda() {
        Map<String,Artigo> newmap = new HashMap<String,Artigo>();
        for(Map.Entry<String,Artigo> m : produtosParaVenda.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    public Map<String,Artigo> getProdutosAdquiridos(){
        Map<String,Artigo> newmap = new HashMap<String,Artigo>();
        for(Map.Entry<String,Artigo> m : produtosAdquiridos.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    //falta adaptar os getters e setters do carrinho para uma encomenda
    public Map<String,Artigo> getCarrinho(){
        Map<String,Artigo> newmap = new HashMap<String,Artigo>();
        for(Map.Entry<String,Artigo> m : carrinho.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    public Map<String,Encomenda> getComprasFeitas(){
        Map<String,Encomenda> newmap = new HashMap<String,Encomenda>();
        for(Map.Entry<String,Encomenda> m : comprasFeitas.entrySet())
            newmap.put(m.getKey(), m.getValue().clone());
        return newmap;
    }

    public double getValorVendas() {
        return valorVendas;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setNumeroFiscal(int numeroFiscal) {
        this.numeroFiscal = numeroFiscal;
    }

    public void setProdutosVendidos(Map<String,Artigo> produtosvendidos) {
        this.produtosVendidos = new HashMap<>();
        for(String l : produtosvendidos.keySet())
            this.produtosVendidos.put(l,produtosvendidos.get(l).clone());
    }

    public void setProdutosParaVenda(Map<String,Artigo> produtosparavenda) {
        this.produtosParaVenda = new HashMap<>();
        for(String l : produtosparavenda.keySet())
            this.produtosParaVenda.put(l,produtosparavenda.get(l).clone());

    }

    public void setProdutosAdquiridos(Map<String,Artigo> produtosAdquiridos) {
        this.produtosAdquiridos = new HashMap<>();
        for(String l : produtosAdquiridos.keySet())
            this.produtosAdquiridos.put(l,produtosAdquiridos.get(l).clone());
    }

    public void setCarrinho(Map<String,Artigo> carrinho) {
        this.carrinho = new HashMap<>();
        for(String l : carrinho.keySet())
            this.carrinho.put(l,carrinho.get(l).clone());
    }

    public void setComprasFeitas(Map<String,Encomenda> comprasFeitas) {
        this.comprasFeitas = new HashMap<>();
        for(String l : comprasFeitas.keySet())
            this.comprasFeitas.put(l,comprasFeitas.get(l).clone());
    }

    public void setValorVendas(double valorVendas) {
        this.valorVendas = valorVendas;
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "codigo='" + codigo + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", morada='" + morada + '\'' +
                ", numeroFiscal=" + numeroFiscal +
                ", produtosVendidos=" + produtosVendidos +
                ", produtosParaVenda=" + produtosParaVenda +
                ", produtosAdquiridos=" + produtosAdquiridos +
                ", carrinho=" + carrinho +
                ", valorVendas=" + valorVendas +
                '}';
    }

    //------------------------------------------------------------------------------------------------------------------

    public double valorVendas(){
        return produtosVendidos.values().stream().mapToDouble(Artigo::calculaPreco).sum();
    }

    public void adicionaArtigoParaVenda(Artigo artigo){
        this.produtosParaVenda.put(artigo.getID(),artigo.clone());
        this.setProdutosParaVenda(produtosParaVenda);
    }

    public String verificaEncomenda(String ID) throws IdEncomendaNaoExistenteException {
        if(!this.comprasFeitas.containsKey(ID)) throw new IdEncomendaNaoExistenteException("Esta encomenda não existe!");
        return ID;
    }

}
