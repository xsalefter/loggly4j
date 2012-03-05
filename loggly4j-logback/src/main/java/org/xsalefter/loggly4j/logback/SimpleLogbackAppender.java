package org.xsalefter.loggly4j.logback;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

/**
 * Very simple Appender for Logback. Although this "just works", but I'll 
 * consider another approach for connection mechanism.
 * 
 * @author xsalefter
 */
public class SimpleLogbackAppender extends AppenderBase<ILoggingEvent> {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	
	private String logglyInput;
	
	@Override
	public void start() {
		if (this.logglyInput == null) {
			addError("You need to specify loggly input in appender named ["+ name +"].");
			return;
		}
		
	    super.start();
	}
	
	@Override
	protected void append(ILoggingEvent eventObject) {
		URL url = null;
		HttpsURLConnection connection = null;
		DataOutputStream outputStream = null;
		
		try {
			url = new URL(this.logglyInput);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setConnectTimeout(2000);
			connection.connect();
			
			outputStream = new DataOutputStream(connection.getOutputStream());
			final byte[] messages = eventObject.getFormattedMessage().getBytes(UTF_8); 
			outputStream.write(messages);
			outputStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			if (connection != null) {
				try {
					final int responseCode = connection.getResponseCode();
					final String responseMessage = connection.getResponseMessage();
					addInfo(">>> Response from Loggly: " + responseCode + " " + responseMessage);
					connection.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public final String getLogglyInput() {
		return logglyInput;
	}

	public final void setLogglyInput(final String logglyInput) {
		this.logglyInput = logglyInput;
	}
	
}
