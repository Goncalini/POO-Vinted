package exceptions;

public class TransportadoraExistenteException extends Exception {

    public TransportadoraExistenteException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}
