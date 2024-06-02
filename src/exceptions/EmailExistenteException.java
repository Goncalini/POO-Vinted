package exceptions;

public class EmailExistenteException extends Exception {

    public EmailExistenteException(String msg){
        super(msg);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}

