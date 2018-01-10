package urlshortener.RedPepper.model;

public class PinPointParameters {

    private boolean redirect;

    private int radio;

    private int resultNumber;

    public void setRedirect(boolean redirect){

        this.redirect = redirect;
    }

    public boolean isRedirect() {

        return redirect;
    }

    public int getRadio() {

        return radio;
    }

    public void setRadio(int radio) {

        this.radio = radio;
    }

    public int getResultNumber() {

        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {

        this.resultNumber = resultNumber;
    }

    @Override
    public String toString() {

        return "PinPointParameters{" +
                "redirect=" + redirect +
                ", radio=" + radio +
                ", resultNumber=" + resultNumber +
                '}';
    }
}
