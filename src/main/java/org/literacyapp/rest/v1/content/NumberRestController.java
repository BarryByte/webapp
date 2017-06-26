package org.literacyapp.rest.v1.content;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.gson.content.NumberGson;
import org.literacyapp.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/content/number", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NumberRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        JSONArray numbers = new JSONArray();
        for (org.literacyapp.model.content.Number number : numberDao.readAllOrdered(locale)) {
            NumberGson numberGson = JavaToGsonConverter.getNumberGson(number);
            String json = new Gson().toJson(numberGson);
            numbers.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("numbers", numbers);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
