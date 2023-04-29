package com.app.cryptography.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("")
public class DecryptController {

    @GetMapping("/decryptedFiles")
    public List<String> getAllFileModel() {
        ArrayList<String> arrayList = new ArrayList<>();
        return arrayList;
    }
}
