package exceptions;

public class IdArtigoNaoExistenteException extends Exception{

    public IdArtigoNaoExistenteException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}
