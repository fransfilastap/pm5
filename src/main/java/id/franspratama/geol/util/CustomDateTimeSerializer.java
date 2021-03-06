package id.franspratama.geol.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateTimeSerializer extends JsonSerializer<Date>{

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
	    String dateString = dateFormat.format(date);
	    Date newDate = null;
		try {
			Date xDate = dateFormat.parse(dateString);
			newDate = DateUtils.addHours(xDate, 7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    long milis = newDate.getTime();
	    jsonGenerator.writeNumber(milis);
		
	}

}
