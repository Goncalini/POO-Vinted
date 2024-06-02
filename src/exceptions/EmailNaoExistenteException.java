package exceptions;

public class EmailNaoExistenteException extends Exception{

    public EmailNaoExistenteException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}
