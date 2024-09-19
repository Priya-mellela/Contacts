package com.contacts.Management.repository;

import com.contacts.Management.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Override
    @Query("SELECT c FROM ContactEntity c WHERE c.isActive = true")
    List<ContactEntity> findAll();

    @Query("SELECT c FROM ContactEntity c WHERE c.id = :id AND c.isActive = true")
    Optional<ContactEntity> findActiveById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE ContactEntity c SET c.isActive = false WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE ContactEntity c SET c.isActive = false WHERE c.id IN :ids")
    void batchSoftDeleteByIds(@Param("ids") List<Long> ids);
}
