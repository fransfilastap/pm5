package id.franspratama.geol.web.controller;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.text.WordUtils;

import id.franspratama.geol.core.pojo.TimeSpan;

public class TimespanEnumConverter extends PropertyEditorSupport{
    
	@Override 
    public void setAsText(final String text) throws IllegalArgumentException
    {
        setValue(TimeSpan.valueOf(WordUtils.capitalizeFully(text.trim())));
    }
}
