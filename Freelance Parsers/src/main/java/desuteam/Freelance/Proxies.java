package desuteam.Freelance;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Proxies {
	
	private static List<Proxy> allProxies, testedProxies;
	private static int currentIndex;
	
	static{
		allProxies = new ArrayList<Proxies.Proxy>();
		testedProxies = new ArrayList<Proxies.Proxy>();
	};
	
	public static void initDefault(){
		initDefaultProxies();
		testProxies();
	}
	
	private static void initDefaultProxies(){

		addProxy("192.151.159.115:8118");
		addProxy("1.162.59.138:8080");
		addProxy("47.89.53.92:3128");
		addProxy("219.141.225.107:80");
		addProxy("178.57.115.124:8080");
		addProxy("189.20.255.211:8080");
		addProxy("61.227.113.73:8080");
		addProxy("64.33.135.190","8080");
		addProxy("13.91.254.82","8080");
		addProxy("162.144.57.157","80");
		addProxy("68.168.220.108","8080");
		addProxy("162.144.111.48","80");
		addProxy("192.249.72.148","3128");
		addProxy("104.236.55.48","8080");
		addProxy("73.215.92.221","8080");
		addProxy("64.151.104.229","80");
		addProxy("104.236.54.155","8080");
		addProxy("152.160.35.171","80");
		addProxy("104.236.49.154","8080");
		addProxy("209.159.150.210","8080");
		addProxy("64.20.37.93","8080");
		addProxy("46.101.22.124","8118");
		addProxy("46.101.3.126","8118");
		addProxy("117.169.4.43","843");
		addProxy("168.63.24.174","8121");
		addProxy("158.181.151.181","3128");
		addProxy("124.88.67.32","843");
		addProxy("218.191.247.51","8380");
		addProxy("106.38.251.63","8088");
		addProxy("124.88.67.24","843");
		addProxy("124.240.187.78","81");
		addProxy("124.88.67.17","80");
		addProxy("40.113.118.174","8120");
		addProxy("124.88.67.31","83");
		addProxy("124.88.67.30","843");
		addProxy("58.176.46.248","80");
		addProxy("212.227.159.39","80");
		addProxy("51.254.106.73","80");
		addProxy("63.150.152.151","8080");
		addProxy("120.25.235.11","8089");
		addProxy("139.196.140.9","80");
		addProxy("84.28.221.68","80");
		addProxy("219.255.197.90","3128");
		addProxy("46.218.85.101","3129");
		addProxy("119.29.103.13","8888");
		addProxy("203.210.6.39","80");
		addProxy("39.88.108.35","81");
		addProxy("124.88.67.32","83");
		addProxy("14.63.226.198","80");
		addProxy("119.18.234.60","80");
		addProxy("91.194.42.51","80");
		addProxy("173.161.0.227","80");
		addProxy("27.202.234.98","9999");
		addProxy("183.57.17.194","8081");
		addProxy("113.253.13.205","80");
		addProxy("124.88.67.32","80");
		addProxy("123.56.74.13","8080");
		addProxy("87.255.70.228","3128");
		addProxy("218.4.114.70","8080");
		addProxy("151.80.57.55","8080");
		addProxy("124.88.67.30","83");
		addProxy("223.16.229.8","8080");
		
	}
	
	public static void addProxy(String ip){
		String[] data = ip.split(":");
		addProxy(data[0], data[1]);
	}
	public static void addProxy(String host, String port){
		allProxies.add(new Proxy(host, port));
	}
	
	public static void rem(){
		testedProxies.remove(currentIndex);
		System.out.println("			[Size: " + testedProxiesSize() + "]");
	}
	public static void testProxies(){
		System.out.println("Proxies test:");
		currentIndex = 0;
		testedProxies.clear();
		
		for(Proxy proxy : allProxies){
			System.out.print(proxy + "		");
			try {
				if(testProxy(proxy)){ 
					int ping = testPingTime(proxy);
					System.out.println("OK[" + ping+"ms]");
					testedProxies.add(proxy); 
				}
				else
					System.out.println("FAIL");
			} catch (IOException e) {}
		}
		
	}
	private static boolean testProxy(Proxy proxy) throws IOException{
		return InetAddress.getByName(proxy.host).isReachable(1000);
	}
	private static int testPingTime(Proxy proxy) throws IOException{
		int allTime = 0;
		
		for(int i = 0;i < 1;i++){
			long time = System.nanoTime();
			InetAddress.getByName(proxy.host).isReachable(3000);
			allTime += (int)((System.nanoTime() - time)/1000000L);
		}
		return allTime/1;
	}
	
	public static int testedProxiesSize(){
		return testedProxies.size();
	}
	
	public static void setNextProxy(boolean print){
		if(testedProxiesSize() == 0) return;
		if(currentIndex >= testedProxiesSize())
			currentIndex = 0;
		
		if(print)
			System.out.println("[Set proxy: "+testedProxies.get(currentIndex)+" ]");
		System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", testedProxies.get(currentIndex).host);
        System.setProperty("http.proxyPort", testedProxies.get(currentIndex).port);
        currentIndex++;
	}
	
	private static class Proxy {
		String host;
		String port;
		
		public Proxy(String host, String port) {
			this.host = host;
			this.port = port;
		}
		
		@Override
		public String toString() {
			return String.format("%15s:%5s", host, port);
		}
		
	}

	public static void removeCurrent() {
		allProxies.remove(testedProxies.get(currentIndex - 1));
		if(allProxies.size() <= 5){
			allProxies.clear();
			initDefault();
			testProxies();
		}
		else
			System.out.println("[Proxy list size: "+allProxies.size()+"]");
	}	
	
}
