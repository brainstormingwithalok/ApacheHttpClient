package brainstorming.with.alok.ApacheHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

import org.apache.http.HttpResponse;

public class GetMethodCall {
	

	public static void main(String[] args) throws IOException, URISyntaxException {
		String endpoint="api/users";
		HttpCallActions.GET(endpoint, HttpCallActions.getSSLCustomClient());
		HttpResponse httpResponse=HttpCallActions.getResponse();
		System.out.println(httpResponse.getStatusLine().getStatusCode());
		
		//Printing the response
		Scanner sc=new Scanner(httpResponse.getEntity().getContent());
		while(sc.hasNext()) {
			System.out.print(sc.next());
		}	
	}

}
