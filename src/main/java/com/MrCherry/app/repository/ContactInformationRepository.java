package com.MrCherry.app.repository;

import com.MrCherry.app.model.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation,Long> {
}
