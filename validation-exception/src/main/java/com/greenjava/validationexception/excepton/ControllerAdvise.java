package com.greenjava.validationexception.excepton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ControllerAdvise {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse badException(BadRequestException e){

        ExceptionResponse exceptionResponse=new ExceptionResponse();
        exceptionResponse.setDate(new Date());
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }


    // default message handler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse runtimeException(Exception e){

        ExceptionResponse exceptionResponse=new ExceptionResponse();
        exceptionResponse.setDate(new Date());
        exceptionResponse.setMessage("Internal Server Error ");
        e.printStackTrace();
        return exceptionResponse;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse validationException(MethodArgumentNotValidException e){
        ExceptionResponse exceptionResponse=new ExceptionResponse();
        exceptionResponse.setDate(new Date());

        BindingResult bindingResult = e.getBindingResult();
        String field = bindingResult.getFieldError().getDefaultMessage();
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        exceptionResponse.setMessage(errors.toString());
        return exceptionResponse;
    }

    public  String errorToJson(List<String> list)  {
        ObjectMapper objectMapper=new ObjectMapper();
        String s = null;
        try {
            s = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;

    }
}
