package com.allstate.addressProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.allstate.addressProject.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
