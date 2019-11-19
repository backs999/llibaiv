package com.llibaiv.web;

import java.io.IOException;
import java.math.BigDecimal;

import com.llibaiv.model.Product;

import lombok.experimental.UtilityClass;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@UtilityClass
public class RequestHelper {

	private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	private OkHttpClient client = new OkHttpClient();
	
	public String post(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	public String put(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder().url(url).put(body).build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	public String get(String url) throws IOException {
		Request request = new Request.Builder().url(url).get().build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	public Product product(final String name, final double value) {

		return Product.builder().name(name).price(BigDecimal.valueOf(value)).build();
	}	
}
