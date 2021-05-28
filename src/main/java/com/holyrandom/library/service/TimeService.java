package com.holyrandom.library.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TimeService {
    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
