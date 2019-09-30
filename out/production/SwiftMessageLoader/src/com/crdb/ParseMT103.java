package com.crdb;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class ParseMT103 {
    public static HashMap<String, String> MT103(String line) {
         String trxReferenceNumber = "";
         String timeIndication = "";
         String dateCurrencyAndAmount = "";
         String bankOperationCode = "";
         String orderingCustomerOption50K = "";
         String orderingCustomerOption50F = "";
         String orderingCustomerOption50A = "";
         String partyIdentifier = "";
         String senderCorrespondent = "";
         String beneficiaryCustomer = "";
         String remittanceInformation = "";
         String accountNumber = "";
         StringBuilder orderingCustomerNameAndAddress = new StringBuilder();
         StringBuilder beneficiaryNameAndAddress = new StringBuilder();
         StringBuilder identifierCode = new StringBuilder();
         StringBuilder sendingInstitutionIdentifierCode = new StringBuilder();
         StringBuilder orderingInstitutionIdentifierCode = new StringBuilder();
         StringBuilder orderingInstitutionAddressAndName = new StringBuilder();
         String beneficiaryAccount = "";
         String beneficiaryCharges = "";
         String senderBIC = "";
         String customerCode = "";
         String currencyAndInstructedAmount = "";
         String instructedAmount = "";
         String instructedCurrency = "";
         String instructingParty = "";
         String trxTypeCode = "";
         String exchangeRate = "";
         String sendingInstitution = "";
         String sendingInstitutionPartyIdentifier = "";
         String orderingInstitution = "";
         String orderingInstitutionPartyIdentifier = "";
        HashMap<String, String> map = new HashMap<>();
        trxReferenceNumber = StringUtils.substringBetween(line, ":20:", ":");
        if (trxReferenceNumber!=null){
            trxReferenceNumber = trxReferenceNumber.replace("\n","");
        }else {
            trxReferenceNumber = "";
        }
        timeIndication = StringUtils.substringBetween(line, ":13C:", ":");
        if (timeIndication!=null){
            timeIndication = timeIndication.replace("\n","");
        }else {
            timeIndication = "";
        }
        dateCurrencyAndAmount = StringUtils.substringBetween(line, ":32A:", ":");
        bankOperationCode = StringUtils.substringBetween(line, ":23B:", ":");
        if (bankOperationCode!=null){
            bankOperationCode = bankOperationCode.replace("\n","");
        }else{
            bankOperationCode = "";
        }
        senderCorrespondent = StringUtils.substringBetween(line, ":53A:", ":");
        if (senderCorrespondent!=null){
            senderCorrespondent = senderCorrespondent.replace("\n","");
        }else {
            senderCorrespondent = "";
        }
        instructingParty = StringUtils.substringBetween(line,":23E:",":");
        if (instructingParty!=null){
            instructingParty = instructingParty.replace("\n","");
        }else {
            instructingParty = "";
        }
        trxTypeCode = StringUtils.substringBetween(line,":26T:",":");
        if (trxTypeCode!=null){
            trxTypeCode = trxTypeCode.replace("\n","");
        }else {
            trxTypeCode = "";
        }
        exchangeRate = StringUtils.substringBetween(line,":36:",":");
        if (exchangeRate!=null){
            exchangeRate = exchangeRate.replace("\n","");
        }else{
            exchangeRate = "";
        }
        senderBIC = StringUtils.substringBetween(line,"{1:","}");
        currencyAndInstructedAmount = StringUtils.substringBetween(line,"33B:",":");
        if (currencyAndInstructedAmount!=null){
            instructedAmount = currencyAndInstructedAmount.substring(3).replace(",",".").replace("\n","");
            instructedCurrency = currencyAndInstructedAmount.substring(0,3);
        } //====================FIELD 52D(Ordering Institution)====================//
        orderingInstitution = StringUtils.substringBetween(line, ":52D:", ":");
        if (orderingInstitution!=null) {
            Scanner field52D = new Scanner(orderingInstitution);
            List<String> stringList = new ArrayList<>();
            while (field52D.hasNextLine()) {
                String myLine = field52D.nextLine();
                if (myLine != null) {
                    stringList.add(myLine);
                }
            }
            orderingInstitutionPartyIdentifier = stringList.get(0);
            orderingInstitutionPartyIdentifier = orderingInstitutionPartyIdentifier.replace("/", "");
            stringList.remove(0);
            for (String s : stringList) {
                orderingInstitutionAddressAndName.append(s).append(" ");
            }
        }
        //====================FIELD 52A(Ordering Institution)====================//
        orderingInstitution = StringUtils.substringBetween(line, ":52A:", ":");
        if (orderingInstitution!=null) {
            Scanner field52A = new Scanner(orderingInstitution);
            List<String> stringList = new ArrayList<>();
            while (field52A.hasNextLine()) {
                String myLine = field52A.nextLine();
                if (myLine != null) {
                    stringList.add(myLine);
                }
            }
            orderingInstitutionPartyIdentifier = stringList.get(0);
            orderingInstitutionPartyIdentifier = orderingInstitutionPartyIdentifier.replace("/", "");
            stringList.remove(0);
            for (String s : stringList) {
                orderingInstitutionIdentifierCode.append(s).append(" ");
            }
        }
        //====================FIELD 51A=======================================//
        sendingInstitution = StringUtils.substringBetween(line, ":51A:", ":");
        if (sendingInstitution!=null) {
            Scanner field51A = new Scanner(sendingInstitution);
            List<String> stringList = new ArrayList<>();
            while (field51A.hasNextLine()) {
                String myLine = field51A.nextLine();
                if (myLine != null) {
                    stringList.add(myLine);
                }
            }
            sendingInstitutionPartyIdentifier = stringList.get(0);
            sendingInstitutionPartyIdentifier = sendingInstitutionPartyIdentifier.replace("/", "");
            stringList.remove(0);
            for (String s : stringList) {
                sendingInstitutionIdentifierCode.append(s).append(" ");
            }
        }
        //=====================FIELD 50K=====================================//
        orderingCustomerOption50K = StringUtils.substringBetween(line, ":50K:", ":");
        if (orderingCustomerOption50K!=null) {
            Scanner option50k = new Scanner(orderingCustomerOption50K);
            List<String> stringList = new ArrayList<>();
            while (option50k.hasNextLine()) {
                String myLine = option50k.nextLine();
                if (myLine != null) {
                    stringList.add(myLine);
                }
            }
            accountNumber = stringList.get(0);
            accountNumber = accountNumber.replace("/", "");
            stringList.remove(0);
            for (String s : stringList) {
                orderingCustomerNameAndAddress.append(s).append(" ");
            }
            customerCode = accountNumber.substring(4);
            customerCode = customerCode.substring(0, customerCode.length() - 2);
        }
        //=====================FIELD 50A=====================================//
        orderingCustomerOption50A = StringUtils.substringBetween(line, ":50A:", ":");
        if (orderingCustomerOption50A!=null) {
            Scanner option50k = new Scanner(orderingCustomerOption50A);
            List<String> stringList = new ArrayList<>();
            while (option50k.hasNextLine()) {
                String myLine = option50k.nextLine();
                if (myLine != null) {
                    stringList.add(myLine);
                }
            }
            accountNumber = stringList.get(0);
            accountNumber = accountNumber.replace("/", "");
            stringList.remove(0);
            for (String s : stringList) {
                identifierCode.append(s).append(" ");
            }
            customerCode = accountNumber.substring(4);
            customerCode = customerCode.substring(0, customerCode.length() - 2);
        }
        //=====================FIELD 50F=====================================//
        orderingCustomerOption50F = StringUtils.substringBetween(line, ":50F:", ":");
        if (orderingCustomerOption50F!=null) {
            Scanner option50k = new Scanner(orderingCustomerOption50F);
            List<String> stringList = new ArrayList<>();
            while (option50k.hasNextLine()) {
                String myLine = option50k.nextLine();
                if (myLine != null) {
                    stringList.add(myLine);
                }
            }
            partyIdentifier = stringList.get(0);
            partyIdentifier = partyIdentifier.replace("/", "");
            stringList.remove(0);
            for (String s : stringList) {
                orderingCustomerNameAndAddress.append(s).append(" ");
            }
        }
        beneficiaryCustomer = StringUtils.substringBetween(line, ":59:", ":");
        Scanner option59 = new Scanner(beneficiaryCustomer);
        List<String> stringList1 = new ArrayList<>();
        while (option59.hasNextLine()) {
            String myLine = option59.nextLine();
            if (myLine != null) {
                stringList1.add(myLine);
            }
        }
        beneficiaryAccount = stringList1.get(0);
        if (beneficiaryAccount!=null){
            beneficiaryAccount = beneficiaryAccount.replace("/","").replace("\n","");
        }
        stringList1.remove(0);
        for (String s : stringList1) {
            beneficiaryNameAndAddress.append(s).append(" ");
        }
        remittanceInformation = StringUtils.substringBetween(line, ":70:", ":");
        if (remittanceInformation!=null){
            remittanceInformation = remittanceInformation.replace("\n","");
        }else {
            remittanceInformation = "";
        }
        String valueDate = UTL.truncate(dateCurrencyAndAmount, 6);
        valueDate = UTL.spaceOut(valueDate, 2, "-");
        valueDate = "20" + valueDate;
        String trxAmount = dateCurrencyAndAmount.substring(9);
        String trxCurrency = dateCurrencyAndAmount.substring(6, 9);
        trxAmount = trxAmount.replace(",", ".");
        if(trxAmount!=null){
            trxAmount = trxAmount.replace("\n","");
        }else {
            trxAmount = "";
        }
        beneficiaryCharges = StringUtils.substringBetween(line, ":71A:", "-}");
        if (beneficiaryCharges != null && beneficiaryCharges.contains(":")) {
            beneficiaryCharges = StringUtils.substringBetween(beneficiaryCharges, "", ":");
            beneficiaryCharges = beneficiaryCharges.replace("\n","");
        } else{
            beneficiaryCharges = StringUtils.substringBetween(line, ":71A:", "-}");
            beneficiaryCharges = beneficiaryCharges.replace("\n","");
        }
        map.put("trxValueDate", valueDate);
        map.put("trxCurrency", trxCurrency);
        map.put("bankOperationCode", bankOperationCode);
        map.put("trxReferenceNumber", trxReferenceNumber);
        map.put("orderingCustomerAccount", accountNumber);
        map.put("orderingCustomerNameAndAddress", orderingCustomerNameAndAddress.toString());
        map.put("trxAmount", trxAmount);
        map.put("senderCorrespondent", senderCorrespondent);
        map.put("beneficiaryAccount", beneficiaryAccount);
        map.put("beneficiaryNameAndAddress", beneficiaryNameAndAddress.toString());
        map.put("timeIndication", timeIndication);
        map.put("remittanceInformation", remittanceInformation);
        map.put("detailsOfCharges", beneficiaryCharges);
        map.put("senderBIC",senderBIC);
        map.put("customerCode",customerCode);
        map.put("instructedAmount",instructedAmount);
        map.put("instructedCurrency",instructedCurrency);
        map.put("instructingParty",instructingParty);
        map.put("trxTypeCode",trxTypeCode);
        map.put("exchangeRate",exchangeRate);
        map.put("identifierCode",identifierCode.toString());
        map.put("partyIdentifier",partyIdentifier);
        map.put("sendingInstitutionPartyIdentifier",sendingInstitutionPartyIdentifier);
        map.put("sendingInstitutionidentifierCode",sendingInstitutionIdentifierCode.toString());
        map.put("orderingInstitutionPartyIdentifier",orderingInstitutionPartyIdentifier);
        map.put("orderingInstitutionIdentifierCode",orderingInstitutionIdentifierCode.toString());
        map.put("orderingInstitutionAddressAndName",orderingInstitutionAddressAndName.toString());
        return map;
    }
}
