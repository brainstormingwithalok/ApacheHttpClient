package brainstorming.with.alok.ApacheHttpClient;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpCallActions {
	public static String baseURI = "https://reqres.in/";
	private static HttpResponse response;

	// Client for SSL
	public static CloseableHttpClient getSSLCustomClient() {
		HttpClientBuilder clientBuilder = HttpClients.custom();
		clientBuilder.setSSLSocketFactory(getSSLContext());
		CloseableHttpClient client = clientBuilder.build();
		return client;
	}

	
	//Post method
	public static void POST(String endPoint, String jsonBody, CloseableHttpClient httpClient) {

		try {
			// Prepare the URL
			URI url = new URIBuilder(baseURI + endPoint).build();
			// Post Request
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/json");
			StringEntity stringEntity = new StringEntity(jsonBody);
			httpPost.setEntity(stringEntity);
			// Execute the request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse != null) {
				response = httpResponse;
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	// Get method
	public static void GET(String endpoint, CloseableHttpClient httpClient) {
		try {
			URI uri = new URIBuilder(baseURI + endpoint).setParameter("page", "2").build();
			HttpGet httpGet = new HttpGet(uri);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null) {
				response = httpResponse;
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

	// Client for multi thread
	public static CloseableHttpClient getConcurrentClient(int threadPoolCount) {
		// Create the pool connection manager
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		// Set the pool size
		connectionManager.setMaxTotal(threadPoolCount);

		// Make the client builder
		HttpClientBuilder clientBuilder = HttpClients.custom();
		// Set the connection manager
		clientBuilder.setConnectionManager(connectionManager);
		// Build the client
		CloseableHttpClient client = clientBuilder.build();
		return client;
	}

	public static HttpResponse getResponse() {
		return response;
	}

	// Default client
	public static CloseableHttpClient getDefaultClient() {
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		return closeableHttpClient;
	}

	// In case your service is secure with SSL and Certs
	private static SSLConnectionSocketFactory getSSLContext() {

		TrustStrategy trustStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				return true;
			}
		};

		HostnameVerifier allVerifier = new NoopHostnameVerifier();
		SSLConnectionSocketFactory connFactory = null;
		try {
			connFactory = new SSLConnectionSocketFactory(
					SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build(), allVerifier);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connFactory;
	}

}
