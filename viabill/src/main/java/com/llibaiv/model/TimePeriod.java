package com.llibaiv.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimePeriod {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate start;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate end;
}
