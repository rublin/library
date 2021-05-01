package com.holyrandom.library.mapper;

import com.holyrandom.library.dto.ClientDetailsDto;
import com.holyrandom.library.dto.ClientSmallDto;
import com.holyrandom.library.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientSmallDto toSmall(Client client);

    ClientDetailsDto toDetails(Client client);
}
