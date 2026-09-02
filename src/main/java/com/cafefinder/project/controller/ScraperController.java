package com.cafefinder.project.controller;

import com.cafefinder.project.model.ResponseDTO;
import com.cafefinder.project.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/cafes")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping
    public ResponseEntity<Set<ResponseDTO>> getAllCafes() {
        Set<ResponseDTO> cafes = scraperService.getCafes();
        return ResponseEntity.ok(cafes);
    }
}