package net.texala.web.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import net.texala.exception.BaseException;
import net.texala.response.entity.RestCustom;
import net.texala.response.entity.RestResponse;
import net.texala.response.entity.RestStatus;

@RestControllerAdvice
public class CustomResponseEntityExceptioHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handling User Defined Exception
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<RestResponse<Object>> handleBaseException(BaseException ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.PRECONDITION_FAILED, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Data Integrity Violation
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<RestResponse<Object>> handleDataIntegrityViolationException(
			DataIntegrityViolationException ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.CONFLICT, "There is some technical issue"), custom);

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	/**
	 * Handling Constraint Violation Exception
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<RestResponse<Object>> handleConstraintViolationException(ConstraintViolationException ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, "Database Constraint Vailation"), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Constraint Violation Exception
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(TransactionSystemException.class)
	protected ResponseEntity<RestResponse<Object>> handleTransactionSystemException(TransactionSystemException ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling No Such Element
	 * 
	 * @param ex
	 * @return
	 */
	protected ResponseEntity<RestResponse<String>> handleNoSuchElementException(NoSuchElementException ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<String> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.NOT_FOUND, "Employee ID is not present"), custom);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handling Method Argument Type Mismatch
	 * 
	 * @param ex
	 * @return
	 */
	protected ResponseEntity<RestResponse<String>> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<String> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.NOT_ACCEPTABLE, "Invalid Employee ID. Required Numeric ID"), custom);

		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Handling Generic Exceptions
	 * 
	 * @param ex
	 * @return
	 */
	//@ExceptionHandler(Exception.class)
	protected ResponseEntity<RestResponse<Object>> handleException(Exception ex) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.NOT_FOUND, "Currently our service is down. Please try after sometime."),
				custom);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handling Request Method Not Supported
	 */

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<String> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Handling Media Type Not Supported
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Handling Media Type Not Acceptable
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.NOT_ACCEPTABLE, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Handling Missing Path Variable
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handling Missing Servlet Request Parameter
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Servlet Request Binding
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Conversion Not Supported
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handling Type Mismatch
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Message Not Readable
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Message Not Writable
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handling Method Argument Not Valid
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {

		final Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage() + "\n");
		});

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, errorMap.toString()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	/**
	 * Handling Missing Servlet Request Part
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling Bind Exception
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.BAD_REQUEST, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handling No Handler Found
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.NOT_FOUND, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handling Asynch Request Timeout
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		final RestCustom custom = RestCustom.builder().build();
		custom.setMessage(ex);
		final RestResponse<Object> response = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage()), custom);

		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}
}