package brainstorming.with.alok.ApacheHttpClient;

import java.util.concurrent.CountDownLatch;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpGetRequestUsingThread extends Thread {

	CloseableHttpClient httpClient;
	HttpGet getRequest;
	HttpPost postRequest;
	int id;
	CountDownLatch countDownLatch;

	public HttpGetRequestUsingThread(CloseableHttpClient httpClient, HttpGet getRequest, int id,
			CountDownLatch countDownLatch) {
		this.httpClient = httpClient;
		this.getRequest = getRequest;
		this.id = id;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {

		try {
			CloseableHttpResponse httpresponse = httpClient.execute(getRequest);
			// Status
			System.out.println("Current status of thread " + id + ":" + httpresponse.getStatusLine());
			//Consume to release connection
			EntityUtils.consumeQuietly(httpresponse.getEntity());

		}
		catch (Exception e) {
			e.getMessage();
		} finally {
			countDownLatch.countDown();
		}

	}
}
