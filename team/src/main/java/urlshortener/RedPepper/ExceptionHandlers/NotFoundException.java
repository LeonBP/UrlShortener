package urlshortener.RedPepper.ExceptionHandlers;

//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

public class NotFoundException extends ApiException {
    private int code;

    public NotFoundException(String msg, int code) {
        super(code, msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
