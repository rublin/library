package com.holyrandom.library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookHistoryDto {
    private ClientSmallDto client;
    private LocalDate inUseFrom;
    private LocalDate inUseTo;
}
