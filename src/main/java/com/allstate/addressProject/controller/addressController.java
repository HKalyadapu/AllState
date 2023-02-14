package com.allstate.addressProject.controller;

import com.allstate.addressProject.entity.Address;
import com.allstate.addressProject.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class addressController {

    @Autowired
    private AddressBookService addressBookService;


    //api call for adding an address to the addressBook
    @PostMapping("/api/v1/addAddress")
    public ResponseEntity<String> addAddressToAddressBook(@Valid @RequestBody Address newAddress) {
        Address addressBook = addressBookService.addAddress(newAddress);
        if(addressBook!=null)
            return new ResponseEntity<String>("Address added with ID: "+addressBook.getId(), HttpStatus.CREATED);
        else{
            return new ResponseEntity<String>("Duplicate entries cannot be added",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //api call for deleting an address from the addressBook
    @DeleteMapping("/api/v1/deleteAddress/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable(value = "id") Integer addressId) throws EmptyResultDataAccessException{
        boolean result = addressBookService.removeAddress(addressId);
        if(result){
            return new ResponseEntity<String>("Deleted address with ID: "+addressId, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("The address does not exist",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //api call for getting all the addresses from the addressBook
    @GetMapping("/api/v1/getAddressList")
    public ResponseEntity<List<Address>> getAddressList() {
        List<Address> addressList = addressBookService.getAddressList();
        return new ResponseEntity<List<Address>>(addressList, HttpStatus.OK);
    }

    //api call for modifying data of a specific id from the addressBook
    @PutMapping("/api/v1/modifyAddress")
    public ResponseEntity<String> modifyAddress(@Valid @RequestBody Address existingAddress) {
        Address address = addressBookService.modifyAddress(existingAddress);
        if(address!=null) {
            return new ResponseEntity<String>("Modified address with ID: " + address.getId(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<String>("The address does not exist ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
