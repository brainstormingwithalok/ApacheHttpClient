package brainstorming.with.alok.ApacheHttpClient;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class MultiThreadClientExecution {

	public static void main(String aa[]) throws IOException, InterruptedException {
		//Number of thread
		int numberOfThread=10;
		//Create URL with endPoint
		URI url = URI.create("https://reqres.in/api/users/2");
		CloseableHttpClient httpClient = HttpCallActions.getConcurrentClient(20);
		
		CountDownLatch countDownLatch=new CountDownLatch(numberOfThread);
		try {
			getRequests(httpClient, url.toString(), numberOfThread,countDownLatch);
			//Wait for all thread to complete
			countDownLatch.await();
		} finally {
			httpClient.close();
		}

	}

	public static void getRequests(CloseableHttpClient closeableHttpClient, String url, int threadCount,CountDownLatch countDownLatch) {
		try {
			for (int i = 0; i < threadCount; i++) {
				//Create request
				HttpGet httpGet = new HttpGet(url);
				HttpGetRequestUsingThread httpGetRequestUsingThread = new HttpGetRequestUsingThread(closeableHttpClient,
						httpGet, i,countDownLatch);
				//Start the thread
				httpGetRequestUsingThread.start();
			}
			
		} catch (Exception e) {
			e.getMessage();
		}

	}

}