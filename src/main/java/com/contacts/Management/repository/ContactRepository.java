package com.contacts.Management.repository;

import com.contacts.Management.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    List<ContactEntity> findAllByIsActive(@Param("isActive") Boolean isActive);

    @Query(value = "SELECT * FROM contacts c WHERE c.id = :id AND c.is_active = true",nativeQuery = true)
    Optional<ContactEntity> findActiveById(@Param("id") Long id);

    @Modifying
    @Query(value="UPDATE contacts c SET c.is_active = false WHERE c.id = :id", nativeQuery = true)
    void softDeleteById(@Param("id") Long id);

    @Modifying
    @Query(value= "UPDATE contacts c SET c.is_active = false WHERE c.id IN :ids",nativeQuery = true)
    void batchSoftDeleteByIds(@Param("ids") List<Long> ids);
}
