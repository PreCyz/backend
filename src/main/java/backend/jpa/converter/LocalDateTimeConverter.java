package backend.jpa.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		Date in = new Date();
		LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date date) {
		if (date == null) {
			return null; 
		}
		Instant instant = date.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		return zdt.toLocalDateTime();
	}

}
