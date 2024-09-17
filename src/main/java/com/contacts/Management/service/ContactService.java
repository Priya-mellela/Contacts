package com.contacts.Management.service;

import com.contacts.Management.dto.ContactUser;
import com.contacts.Management.entity.ContactEntity;
import com.contacts.Management.exception.ContactServiceException;
import com.contacts.Management.mapper.ContactMapper;
import com.contacts.Management.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }


    public ContactUser createContact(ContactUser contactUser) throws ContactServiceException {
        ContactEntity contactEntity = contactMapper.toEntity(contactUser);
        contactEntity.setCreatedDate(java.time.LocalDateTime.now());
        ContactEntity savedContact = contactRepository.save(contactEntity);
        return contactMapper.toDto(savedContact);
    }


    public ContactUser getContactById(Long id) throws ContactServiceException {
        ContactEntity contactEntity = contactRepository.findById(id)
                .orElseThrow(() -> new ContactServiceException("Contact not found", HttpStatus.NOT_FOUND));
        return contactMapper.toDto(contactEntity);
    }


    public List<ContactUser> getAllContacts() throws ContactServiceException {
        List<ContactEntity> contactEntities = contactRepository.findAll();
        return contactEntities.stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }


    public ContactUser updateContact(Long id, ContactUser contactUser) throws ContactServiceException {
        ContactEntity existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactServiceException("Contact not found", HttpStatus.NOT_FOUND));


        contactMapper.toEntity(contactUser);
        existingContact.setUpdatedDate(java.time.LocalDateTime.now());

        ContactEntity updatedContact = contactRepository.save(existingContact);
        return contactMapper.toDto(updatedContact);
    }


    public void deleteContact(Long id) throws ContactServiceException {
        if (!contactRepository.existsById(id)) {
            throw new ContactServiceException("Contact not found", HttpStatus.NOT_FOUND);
        }
        contactRepository.deleteById(id);
    }


    public void batchDeleteContacts(List<Long> contactIds) throws ContactServiceException {
        List<ContactEntity> contactsToDelete = contactRepository.findAllById(contactIds);
        if (contactsToDelete.size() != contactIds.size()) {
            throw new ContactServiceException("Some contacts not found", HttpStatus.NOT_FOUND);
        }
        contactRepository.deleteAll(contactsToDelete);
    }
}
