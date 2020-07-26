package br.com.umdesenvolvedor.excption;

public class BussinesException extends RuntimeException {
	private static final long serialVersionUID = -2046495990492358841L;

	public BussinesException(String mensagem) {
		super(mensagem);
	}

}
