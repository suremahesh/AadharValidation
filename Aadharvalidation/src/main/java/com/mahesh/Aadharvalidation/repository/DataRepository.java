package com.mahesh.Aadharvalidation.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mahesh.Aadharvalidation.entities.AadharImageFileInfo;



@Repository
public interface DataRepository extends JpaRepository<AadharImageFileInfo, String> {
	

}
