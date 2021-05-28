package com.holyrandom.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpiredInUseDto {
    private ClientSmallDto client;
    private BookSmallDto book;
    private LocalDate inUseFrom;
    private Long expiredDays;
}
