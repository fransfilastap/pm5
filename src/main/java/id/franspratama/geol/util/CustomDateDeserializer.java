package id.franspratama.geol.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException, JsonProcessingException {
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        String date = jsonparser.getText();
	        try {
	            return format.parse(date);
	        } catch (ParseException e) {
	            throw new RuntimeException(e);
	        }
	}

}
