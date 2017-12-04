package banhang.smartbill.Entity;

/**
 * Created by KARATA on 04/12/2017.
 */

///Allow user is know that, they haven't right to access something
public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(){

    }

    public UnauthorizedAccessException(String message){
        super(message);
    }
}
