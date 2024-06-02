package exceptions;

public class IdEncomendaNaoExistenteException extends Exception{

    public IdEncomendaNaoExistenteException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}
