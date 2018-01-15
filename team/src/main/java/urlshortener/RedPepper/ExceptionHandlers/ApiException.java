package urlshortener.RedPepper.ExceptionHandlers;

//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

public class ApiException extends Exception{
    public int getCode() {
        return code;
    }

    private int code;
    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }
}
