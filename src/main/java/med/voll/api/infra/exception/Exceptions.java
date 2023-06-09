package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;

@RestControllerAdvice
public class Exceptions {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity error404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity error400(MethodArgumentNotValidException ex) {
		
		var erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity errorRegraDeNegocio(ValidacaoException ve) {
		return ResponseEntity.badRequest().body(ve.getMessage());
	}
	
	private record DadosErroValidacao(String campo, String mensagem) {
		
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
	
	

}
