package exceptions;

public class IdArtigoExistenteException extends  Exception{

    public IdArtigoExistenteException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}
