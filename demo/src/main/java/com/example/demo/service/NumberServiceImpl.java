package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;

@Service
@Slf4j
public class NumberServiceImpl implements NumberService{
    @Override
    public Map<String,String> getFormatedNum(String[] numList) {

        Map<String,String> scientificNotionNumMap = new HashMap<>();

        int n = numList.length;
        List<String> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            String currNum = numList[i];

            if(currNum.isEmpty() || !isValidNum(currNum)){
                scientificNotionNumMap.put(currNum,"Invalid Num");
                continue;
            }
            if(currNum.contains(".")){
                String[] str = currNum.split("\\.");
                String beforeDecimal = str[0];
                String afterDecimal = str[1];
                if((beforeDecimal.isEmpty() || Integer.parseInt(beforeDecimal)==0) && !afterDecimal.isEmpty() ){
                    checkForLessThenZero(scientificNotionNumMap, currNum, afterDecimal);
                }else{
                    checkForGreaterThenOne(scientificNotionNumMap, i, currNum, beforeDecimal,afterDecimal);
                }
            }else{
                checkForInteger(scientificNotionNumMap, currNum);
            }
        }
        return scientificNotionNumMap;
    }

    private void checkForLessThenZero(Map<String, String> scientificNotionNumMap, String currNum, String afterDecimal) {
        int afterDecimalNum  = Integer.parseInt(afterDecimal);
        if(afterDecimalNum==0){
            scientificNotionNumMap.put(currNum,"0");
            return;
        }
        String afterNum = String.valueOf(afterDecimalNum);
        StringBuilder sb = new StringBuilder();
        sb.append(afterNum);
        int exponent = afterNum.length()-1;
        sb.append("e").append(exponent*-1);
        scientificNotionNumMap.put(currNum,sb.toString());
    }

    private void checkForGreaterThenOne(Map<String, String> scientificNotionNumMap, int i, String currNum, String beforeDecimal, String afterDecimal) {
        StringBuilder sb= new StringBuilder();

        sb.append(beforeDecimal.charAt(0));
        int exponent = beforeDecimal.length()-1;

        int m = Math.min(beforeDecimal.length(),5);
        for(int j=1;j<m;j++){
            sb.append(beforeDecimal.charAt(i));
        }
        if(sb.toString().length()<5){
            sb.append(afterDecimal);
        }
        sb.append("e").append(exponent);
        scientificNotionNumMap.put(currNum,sb.toString());
    }

    private void checkForInteger(Map<String, String> scientificNotionNumMap, String currNum) {
        int exponent = currNum.length()-1;
        StringBuilder sb = new StringBuilder();
        sb.append(currNum.charAt(0)).append(".");
        int len = Math.min(currNum.length(),5);
        for(int j=1;j<len;j++){
            sb.append(currNum.charAt(j));
        }

        if(sb.toString().length()>0 ){
            sb.append("e").append(exponent);
            scientificNotionNumMap.put(currNum,sb.toString());
        }
    }

    public boolean isValidNum(String num){
        try{
            Double.parseDouble(num);
        }catch(NumberFormatException ex){
            log.info(num+" invalid number ");
            return false;
        }
        return true;
    }
}
