package com.crio.project.onlineGrocery.controller;

import com.crio.project.onlineGrocery.dto.CustomerDto;
import com.crio.project.onlineGrocery.exception.ResourceNotFoundException;
import com.crio.project.onlineGrocery.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @GetMapping(path="/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId){
        Optional<CustomerDto> customerDto=customerService.getCustomerById(customerId);
        return customerDto.map(customerDto1 -> ResponseEntity.ok(customerDto1))
                .orElseThrow(()->new ResourceNotFoundException("Customer with id "+customerId+" does not exist!"));
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping()
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerDto customerDto){
        CustomerDto savedCustomer=customerService.createCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping(path="/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto,@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.updateCustomer(customerDto,customerId));
    }

    @DeleteMapping(path="/{customerId}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable Long customerId ){
        boolean isDeleted=customerService.deleteCustomerById(customerId);
        if(isDeleted){
            return ResponseEntity.ok(true);
        }return ResponseEntity.notFound().build();
    }
}
