package regressionTests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class download {
	@Test
	public void f() {
		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("--headless");
		options.addArguments("--disable-extensions"); // to disable browser extension popup

		ChromeDriverService driverService = ChromeDriverService.createDefaultService();
		ChromeDriver driver = new ChromeDriver(driverService, options);

		Map<String, Object> commandParams = new HashMap<>();
		commandParams.put("cmd", "Page.setDownloadBehavior");
		Map<String, String> params = new HashMap<>();
		params.put("behavior", "allow");
		params.put("downloadPath", "SofieKDFHeadless\\Downloads");
		commandParams.put("params", params);
		ObjectMapper objectMapper = new ObjectMapper();
		HttpClient httpClient = HttpClientBuilder.create().build();
		String command = null;
		try {
			command = objectMapper.writeValueAsString(commandParams);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
		HttpPost request = new HttpPost(u);
		request.addHeader("content-type", "application/json");
		try {
			request.setEntity(new StringEntity(command));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			httpClient.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.get("http://www.seleniumhq.org/download/");
		driver.findElement(By.linkText("32 bit Windows IE")).click();
	}
}
