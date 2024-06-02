package exceptions;

public class TransportadoraNaoExistenteException extends Exception{

    public TransportadoraNaoExistenteException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }
}
