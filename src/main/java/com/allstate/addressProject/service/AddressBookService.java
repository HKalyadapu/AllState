package com.allstate.addressProject.service;

import com.allstate.addressProject.entity.Address;
import com.allstate.addressProject.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookService {

    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address addressBook) {
        /*In order to prevent the update address operation in save function,
        iam initially checking if an address is already present. If not present then it will added as a new record.*/
        if(addressRepository.findById(addressBook.getId()).isPresent())
            return null;
        else {
            addressRepository.save(addressBook);
            return addressBook;
        }
    }

    public boolean removeAddress(int addressId){
        //Delete an address by using the id
        if(addressRepository.findById(addressId).isPresent())
        {
            addressRepository.deleteById(addressId);
            return true;
        }
        else
        {
            return false;
        }
    }

    public Address modifyAddress(Address addressBook) {
        //Modify the address if the address exists.
        if(addressRepository.findById(addressBook.getId()).isPresent()) {
            addressRepository.save(addressBook);
            return addressBook;
        }
        else{
            return null;
        }
    }

    public List<Address> getAddressList() {
        List<Address> addressList = new ArrayList<Address>();
        addressRepository.findAll().forEach(address1 -> addressList.add(address1));
        return addressList;
    }

}
