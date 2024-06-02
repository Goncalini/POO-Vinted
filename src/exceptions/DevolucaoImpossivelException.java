package exceptions;

public class DevolucaoImpossivelException extends Exception{

    public DevolucaoImpossivelException(String message) {
        super(message);
    }

    public String getMensagem(){
        return this.getMessage();
    }

}
