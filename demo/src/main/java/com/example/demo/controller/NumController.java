package com.example.demo.controller;


import com.example.demo.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/num")
public class NumController {

    @Autowired
    NumberService numberService;

    @GetMapping("/lis/{number}")
    public ResponseEntity<Map<String,String>> formatNum(@PathVariable("number") String nums){
        String[] numberList = nums.split(",");
        Map<String,String> resp =numberService.getFormatedNum(numberList);
        return ResponseEntity.ok(resp);
    }
}
