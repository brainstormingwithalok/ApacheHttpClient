package brainstorming.with.alok.ApacheHttpClient;

import java.util.Scanner;

import org.apache.http.HttpResponse;

public class PostMethodCall {

	public static void main(String[] args) {
		Scanner sc = null;
		try {
			String endpoint = "api/users";
			String jsonBody = "{\n" + "    \"name\": \"morpheus\",\n" + "    \"job\": \"leader\"\n" + "}";
			// Call the request
			HttpCallActions.POST(endpoint, jsonBody, HttpCallActions.getSSLCustomClient());
			// Get the response
			HttpResponse response = HttpCallActions.getResponse();
			System.out.println("Status-code->" + response.getStatusLine().getStatusCode());
			// Print the response
			sc = new Scanner(response.getEntity().getContent());
			while (sc.hasNext()) {
				System.out.println(sc.next());
			}
		} catch (Exception e) {
			e.getMessage();
		}

	}

}
