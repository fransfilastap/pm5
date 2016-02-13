package id.franspratama.geol.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateToStringSerializer extends JsonSerializer<Date>{

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializableProvider)
			throws IOException, JsonProcessingException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String stringDate = dateFormat.format(date);
		
		jsonGenerator.writeString(stringDate);
	}

}
