package com.crio.project.onlineGrocery.service;

import com.crio.project.onlineGrocery.dto.CustomerDto;
import com.crio.project.onlineGrocery.entity.CustomerEntity;
import com.crio.project.onlineGrocery.exception.ResourceNotFoundException;
import com.crio.project.onlineGrocery.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository,ModelMapper modelMapper){
        this.customerRepository=customerRepository;
        this.modelMapper=modelMapper;
    }
    public Optional<CustomerDto> getCustomerById(Long id){
        return customerRepository.findById(id).map(customer->modelMapper.map(customer,CustomerDto.class));
    }
    public boolean deleteCustomerById(Long id){
        if(!customerRepository.existsById(id)){
            throw new ResolutionException("Customer with id"+id+" does not exist");
        }
        customerRepository.deleteById(id);
        return true;
    }

    public List<CustomerDto> getAllCustomers(){
        return customerRepository.findAll().stream()
                .map(customer->modelMapper.map(customer,CustomerDto.class))
                .collect(Collectors.toList());
    }

    public CustomerDto createCustomer(CustomerDto customerDto){
        CustomerEntity customerEntity=modelMapper.map(customerDto,CustomerEntity.class);
        return modelMapper.map(customerRepository.save(customerEntity),CustomerDto.class);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto,Long id){
        if(!customerRepository.existsById(id)){
            throw new ResourceNotFoundException("Customer with id "+id+" does not exists");
        }
        CustomerEntity existingCustomer=customerRepository.findById(id).get();
        modelMapper.map(customerDto,existingCustomer);
        CustomerEntity updatedCustomer=customerRepository.save(existingCustomer);
        return modelMapper.map(updatedCustomer,CustomerDto.class);
    }
}
