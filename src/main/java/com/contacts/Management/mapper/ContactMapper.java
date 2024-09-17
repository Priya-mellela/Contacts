package com.contacts.Management.mapper;

import com.contacts.Management.dto.ContactUser;
import com.contacts.Management.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactEntity toEntity(ContactUser contactUser);
    ContactUser toDto(ContactEntity contactEntity);
}
