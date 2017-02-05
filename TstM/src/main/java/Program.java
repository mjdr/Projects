import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {

	// client configuration
	private static final String clientID = "581786658708-elflankerquo1a6vsckabbhn25hclla0.apps.googleusercontent.com";
	private static final String clientSecret = "3f6NggMbPtrmIBpgx-MK2xXK";
	private static final String authorizationEndpoint = "https://accounts.google.com/o/oauth2/v2/auth";
	private static final String tokenEndpoint = "https://www.googleapis.com/oauth2/v4/token";
	private static final String userInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";

	public Program() {
		// testSha256();
		// testBase64UrlEncodeNoPadding();
		// testRandomDataBase64Url();
		try {
			button_Click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void button_Click() throws IOException, URISyntaxException {
		// Generates state and PKCE values.
		String state = randomDataBase64Url(32);
		String code_verifier = randomDataBase64Url(32);
		String code_challenge = base64UrlEncodeNoPadding(sha256(code_verifier));
		final String code_challenge_method = "S256";
		// Creates an HttpListener to listen for requests on that redirect URI.
		Server server = new Server();
		int port = server.bind();
		output("Listening..");

		// Creates a redirect URI using an available port on the loopback
		// address.
		String redirectURI = String.format("http://localhost:%d/", port);
		output("redirect URI: " + redirectURI);

		// Creates the OAuth 2.0 authorization request.
		String authorizationRequest = String.format(
				"%s?response_type=code&scope=openid%%20profile&redirect_uri=%s&client_id=%s&state=%s&code_challenge=%s&code_challenge_method=%s",
				authorizationEndpoint, new URL(redirectURI).toURI().toString(), clientID, state, code_challenge,
				code_challenge_method);

		// Opens request in the browser.
		openLinkInTheBrowser(authorizationRequest);

		if (!server.waitForRequest(false))
			System.out.println("local server error: " + server.error);

		String code = server.code;
		output("Authorization code: " + code);

		// Starts the code exchange at the Token Endpoint.
		performCodeExchange(code, code_verifier, redirectURI);

		server.unbind();
	}

	public void performCodeExchange(String code, String code_verifier, String redirectURI)
			throws IOException, URISyntaxException {
		output("Exchanging code for tokens...");

		// builds the request
		String tokenRequestURI = "https://www.googleapis.com/oauth2/v4/token";
		String tokenRequestBody = String.format(
				"code=%s&redirect_uri=%s&client_id=%s&code_verifier=%s&client_secret=%s&scope=&grant_type=authorization_code",
				code, new URL(redirectURI).toURI().toString(), clientID, code_verifier, clientSecret);

		// sends the request
		HttpURLConnection tokenRequest = (HttpURLConnection) (new URL(tokenRequestURI).openConnection());
		try {
			byte[] byteVersion = tokenRequestBody.getBytes(StandardCharsets.US_ASCII);
			tokenRequest.setRequestMethod("POST");
			tokenRequest.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			tokenRequest.addRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			tokenRequest.addRequestProperty("Content-Length", Integer.toString(byteVersion.length));
			tokenRequest.setDoOutput(true);

			tokenRequest.connect();

			OutputStream out = tokenRequest.getOutputStream();
			out.write(byteVersion);
			out.close();

			// gets the response
			InputStream inputStream = tokenRequest.getInputStream();
			String responseText = readStreamToEnd(inputStream, false);
			inputStream.close();
			output(responseText);

			Pattern accessTokenPattern = Pattern.compile("\\\"access_token\\\":\\s\\\"([^\\\"]+)");
			Matcher matcher = accessTokenPattern.matcher(responseText);
			if (!matcher.find())
				throw new RuntimeException("Can't find access_token key in json");

			String accessToken = matcher.group(1);
			userinfoCall(accessToken);

		} catch (IOException ex) {
			if (tokenRequest.getResponseCode() != 200) {

				output("HTTP: " + tokenRequest.getResponseCode());
			}

		}
	}

	public void userinfoCall(String accessToken) throws IOException {
		output("Making API Call to Userinfo...");
		// builds the request
		String userinfoRequestURI = "https://www.googleapis.com/oauth2/v3/userinfo";
		// sends the request
		HttpURLConnection userinfoRequest = (HttpURLConnection) (new URL(userinfoRequestURI).openConnection());
		userinfoRequest.setRequestMethod("GET");
		userinfoRequest.addRequestProperty("Authorization", String.format("Bearer %s", accessToken));
		userinfoRequest.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		userinfoRequest.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		userinfoRequest.connect();
		// gets the response

		InputStream inputStream = userinfoRequest.getInputStream();
		output(readStreamToEnd(inputStream, false));
		inputStream.close();

	}

	/**
	 * Appends the given string to the debug console.
	 */
	public void output(String output) {
		System.out.println(output);
	}

	/**
	 * Returns URI-safe data with a given input length.
	 */
	public String randomDataBase64Url(int length) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[length];
		random.nextBytes(bytes);
		return base64UrlEncodeNoPadding(bytes);
	}

	/**
	 * Base64url no-padding encodes the given input buffer.
	 */

	public String base64UrlEncodeNoPadding(byte[] data) {
		String base64 = Base64.getEncoder().encodeToString(data);
		// Converts base64 to base64url.
		base64 = base64.replace("+", "-");
		base64 = base64.replace("/", "_");
		// Strips padding.
		base64 = base64.replace("=", "");
		return base64;
	}

	/**
	 * Returns the SHA256 hash of the input string.
	 */
	public byte[] sha256(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return digest.digest(data.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String readStreamToEnd(InputStream inputStream, boolean emptyLineIsEnd) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = reader.readLine()) != null && !(emptyLineIsEnd && line.isEmpty()))
			buffer.append(line).append('\n');
		return buffer.toString();
	}

	private void openLinkInTheBrowser(String url) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			try {
				desktop.browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Program();
	}
	// Tests

	private void testSha256() {
		byte[] data = sha256("Hello");
		System.out.println(Arrays.toString(data));

	}

	private void testBase64UrlEncodeNoPadding() {
		byte[] data = sha256("Hello");
		System.out.println(base64UrlEncodeNoPadding(data));

	}

	private void testRandomDataBase64Url() {
		System.out.println(randomDataBase64Url(32));
	}

	private void testServer() throws IOException {
		Server server = new Server();
		System.out.println("Port: " + server.bind());
		if (server.waitForRequest(true)) {
			System.out.println("State: " + server.state);
			System.out.println("Code: " + server.code);
		} else System.out.println("Error: " + server.error);
		server.unbind();
	}

	class Server {

		ServerSocket serverSocket;
		Pattern linkPattern = Pattern.compile("GET\\s([^\\s]+)");
		Pattern paramsPattern = Pattern.compile("([\\w]+)=([^&\\s]+)");

		String code, state, error;

		public int bind() throws IOException {
			serverSocket = new ServerSocket(0);
			return serverSocket.getLocalPort();
		}

		public void unbind() throws IOException {
			serverSocket.close();
		}

		public boolean waitForRequest(boolean printHTTPHeaders) throws IOException {
			Socket request = serverSocket.accept();
			InputStream inputStream = request.getInputStream();
			PrintWriter out = new PrintWriter(request.getOutputStream());
			String htmlResponse = "<html><head><meta http-equiv='refresh' content='10;url=https://google.com'></head><body>Please return to the app.</body></html>";

			String httpData = readStreamToEnd(inputStream, true);
			if (printHTTPHeaders)
				output(httpData);
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type: text/html; charset=iso-8859-1");
			out.println("Server: Simple Java server");
			out.println("Content-Length: " + htmlResponse.length());
			out.println("Connection: Closed");
			out.println();
			out.println(htmlResponse);
			out.flush();
			inputStream.close();
			out.close();

			code = null;
			state = null;
			error = null;

			Matcher matcher;
			// Parse url from GET url HTTP/1.1
			matcher = linkPattern.matcher(httpData);
			if (!matcher.find()) {
				error = "Can't find url in http data.";
				return false;
			}
			// Parse url params
			matcher = paramsPattern.matcher(matcher.group(1));
			while (matcher.find()) {
				if (matcher.group(1).equals("code"))
					code = matcher.group(2);
				if (matcher.group(1).equals("state"))
					state = matcher.group(2);
			}

			if (state == null && code == null) {
				error = "State and Code not defined";
				return false;
			}
			if (state == null) {
				error = "State not defined";
				return false;
			}
			if (code == null) {
				error = "Code not defined";
				return false;
			}

			return true;
		}
	}

}
