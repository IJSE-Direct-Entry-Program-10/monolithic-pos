package lk.ijse.dep10.pos.api;

import lk.ijse.dep10.pos.business.BOFactory;
import lk.ijse.dep10.pos.business.BOType;
import lk.ijse.dep10.pos.business.custom.CustomerBO;
import lk.ijse.dep10.pos.dto.CustomerDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {

    @Autowired
    private BasicDataSource pool;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
                    MethodArgumentTypeMismatchException.class})
    public Map<String, Object> handleValidationExceptions(Exception exp){
        HashMap<String, Object> errorAttributes = new LinkedHashMap<>();

        errorAttributes.put("timestamp", LocalDateTime.now().toString());
        errorAttributes.put("status", 400);
        errorAttributes.put("error", HttpStatus.BAD_REQUEST);

        if (exp instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mExp = (MethodArgumentNotValidException) exp;
            errorAttributes.put("message", "Data Validation Failed");
            List<Map<String, Object>> errorList = new ArrayList<>();
            for (FieldError fieldError : mExp.getFieldErrors()) {
                Map<String, Object> error = new LinkedHashMap<>();
                error.put("field", fieldError.getField());
                error.put("rejected", fieldError.getRejectedValue());
                error.put("message", fieldError.getDefaultMessage());
                errorList.add(error);
            }
            errorAttributes.put("errors", errorList);
        }else{
            MethodArgumentTypeMismatchException mExp = (MethodArgumentTypeMismatchException) exp;
            errorAttributes.put("message", "Invalid value for " + mExp.getName());
        }
        return errorAttributes;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody @Valid CustomerDTO customer) throws Exception {
        CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER, pool);
        return customerBO.saveCustomer(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer customerId,
                               @RequestBody @Valid CustomerDTO customer) throws Exception {
        CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER, pool);
        customer.setId(customerId);
        customerBO.updateCustomer(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId) throws Exception {
        CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER, pool);
        customerBO.deleteCustomerById(customerId);
    }

    @GetMapping
    public List<CustomerDTO> getCustomers(@RequestParam(value = "q", required = false)
                                          String query) throws Exception {
        if (query == null) query = "";
        CustomerBO customerBO = BOFactory.getInstance().getBO(BOType.CUSTOMER, pool);
        return customerBO.findCustomers(query);
    }
}
