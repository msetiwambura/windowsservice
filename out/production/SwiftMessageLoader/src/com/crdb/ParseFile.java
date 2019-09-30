package com.crdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class ParseFile {
    static String line="";
    static String blockTwo = "";
    public static void readFile(File file,  DirectoryScanner.OnReadComplete readComplete) throws IOException {
        ArrayList<HashMap<String, String>> arrayMap = new ArrayList<>();
        RestBroker restBroker = new RestBroker();
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line+"\n");
            }
            blockTwo = StringUtils.substringBetween(stringBuffer.toString(), "{2:", "}");
            if (blockTwo != null && blockTwo.contains("I103")) {
                arrayMap.add(ParseMT103.MT103(stringBuffer.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         readComplete.onReadComplete(file.getAbsoluteFile());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("postData", arrayMap);
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        restBroker.jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        restBroker.urlString = "http://172.19.80.198/savvycore/fundtransfers/outgoingtiss/outgoingTissMaintenance";
        restBroker.sendPost();
    }
}
