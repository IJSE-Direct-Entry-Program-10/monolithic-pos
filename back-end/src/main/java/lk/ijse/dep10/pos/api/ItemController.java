package lk.ijse.dep10.pos.api;

import lk.ijse.dep10.pos.business.BOFactory;
import lk.ijse.dep10.pos.business.BOType;
import lk.ijse.dep10.pos.business.custom.ItemBO;
import lk.ijse.dep10.pos.dto.ItemDTO;
import lk.ijse.dep10.pos.dto.util.ValidationGroups;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

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

    @GetMapping("/{itemCode}")
    public ItemDTO getItem(@PathVariable String itemCode) throws Exception {
        ItemBO itemBO = BOFactory.getInstance().getBO(BOType.ITEM, pool);
        return itemBO.findItemByCode(itemCode);
    }

    @GetMapping
    public List<ItemDTO> getItems(@RequestParam(value = "q", required = false) String query) throws Exception {
        if (query == null) query = "";
        ItemBO itemBO = BOFactory.getInstance().getBO(BOType.ITEM, pool);
        return itemBO.findItems(query);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void saveItem(@RequestBody @Validated({ValidationGroups.Save.class}) ItemDTO item) throws Exception {
        ItemBO itemBO = BOFactory.getInstance().getBO(BOType.ITEM, pool);
        itemBO.saveItem(item);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{itemCode:\\d+}", consumes = "application/json")
    public void updateItem(@RequestBody @Valid ItemDTO item,
                           @PathVariable
//                           @Valid
//                           @Pattern(regexp = "\\d+", message = "Invalid value for itemCode")
                           String itemCode) throws Exception {
        ItemBO itemBO = BOFactory.getInstance().getBO(BOType.ITEM, pool);
        item.setCode(itemCode);
        itemBO.updateItem(item);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{itemCode}")
    public void deleteItem(@PathVariable String itemCode) throws Exception {
        ItemBO itemBO = BOFactory.getInstance().getBO(BOType.ITEM, pool);
        itemBO.deleteItemByCode(itemCode);
    }
}
