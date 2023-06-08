package lk.ijse.dep10.pos.business.util;

import lk.ijse.dep10.pos.dto.CustomerDTO;
import lk.ijse.dep10.pos.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TransformerTest {

    private final Transformer transformer = new Transformer();

    @NullSource
    @ValueSource(ints = {1, 2, 5, 10})
    @ParameterizedTest
    void toCustomerEntity(Integer id) {
        CustomerDTO customerDTO = new CustomerDTO(id, "Kasun", "Galle", "078-1234567");
        Customer customerEntity = transformer.toCustomerEntity(customerDTO);
        System.out.println(customerEntity);

        if (customerDTO.getId() == null){
            assertEquals(0, customerEntity.getId());
        }else{
            assertEquals(customerDTO.getId(), customerEntity.getId());
        }
        assertEquals(customerDTO.getName(), customerEntity.getName());
        assertEquals(customerDTO.getAddress(), customerEntity.getAddress());
        assertEquals(customerDTO.getContact(), customerEntity.getContact());
    }

    @Test
    void fromCustomerEntity() {
        Customer customerEntity = new Customer(1, "Kasun", "Galle", "078-1234567");
        CustomerDTO customerDTO = transformer.fromCustomerEntity(customerEntity);

        assertEquals(customerEntity.getId(), customerDTO.getId());
        assertEquals(customerEntity.getName(), customerDTO.getName());
        assertEquals(customerEntity.getAddress(), customerDTO.getAddress());
        assertEquals(customerEntity.getContact(), customerDTO.getContact());
    }
}