package lk.ijse.dep10.pos.api;

import lk.ijse.dep10.pos.dto.ItemDTO;
import lk.ijse.dep10.pos.dto.ResponseErrorDTO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private BasicDataSource pool;

    @GetMapping("/{code}")
    public ResponseEntity<?> getItem(@PathVariable String code) {

    }

    @GetMapping
    public ResponseEntity<?> getItems(@RequestParam(value = "q", required = false) String query) {

    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> saveItem(@RequestBody ItemDTO item) {

    }

    @PatchMapping(value = "/{code}", consumes = "application/json")
    public ResponseEntity<?> updateItem(@RequestBody ItemDTO item, @PathVariable String code) {

    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteItem(@PathVariable String code){

    }
}
