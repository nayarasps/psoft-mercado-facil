package exceptions;

public class PagamentoInvalidoException extends Exception{

    private static final long serialVersionUID = 1L;

    public PagamentoInvalidoException(String erro) {
        super(erro);
    }
}
