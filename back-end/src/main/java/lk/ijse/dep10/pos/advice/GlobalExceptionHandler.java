package lk.ijse.dep10.pos.advice;

import lk.ijse.dep10.pos.business.exception.BusinessException;
import lk.ijse.dep10.pos.business.exception.BusinessExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException exp){
        Map<String, Object> errorAttributes = null;
        if (exp.getType() == BusinessExceptionType.DUPLICATE_RECORD){
            errorAttributes = getCommonErrorAttributes(HttpStatus.CONFLICT);
        }else if (exp.getType() == BusinessExceptionType.RECORD_NOT_FOUND){
            errorAttributes = getCommonErrorAttributes(HttpStatus.NOT_FOUND);
        } else if (exp.getType() == BusinessExceptionType.INTEGRITY_VIOLATION){
            errorAttributes = getCommonErrorAttributes(HttpStatus.BAD_REQUEST);
        }else {
            errorAttributes = getCommonErrorAttributes(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        errorAttributes.put("message", exp.getMessage());
        return new ResponseEntity<>(errorAttributes,
                HttpStatus.valueOf((Integer) errorAttributes.get("status")));
    }

    private Map<String, Object> getCommonErrorAttributes(HttpStatus status){
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now().toString());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status);
        return errorAttributes;
    }
}
