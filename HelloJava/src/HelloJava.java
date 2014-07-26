public class HelloJava {
	private static double lati1=121.76;
	private static double log1=31.05;
	private static double lati2=113.40;
	private static double log2=34.46;
	//private static String time2="2014-07-08 15:01:10";
	
	public final static double EARTH_RADIUS=63781370;
	private static double rad(double d){
		return d*Math.PI/180.0;
	}
	
	public static double get_distance(double lati1,double log1,double lati2, double log2){
		double radLat1 = rad(lati1);
		double radLat2 = rad(lati2);
		double a= radLat1 - radLat2;
		double b=rad(log1)-rad(log2);
		double disdance= 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2), 2)+ Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2), 2)));
		disdance=disdance* EARTH_RADIUS;
		disdance=Math.round(disdance);
		return disdance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double disdance=get_distance(lati1,log1,lati2,log2);
		System.out.println(disdance);
	}

}

