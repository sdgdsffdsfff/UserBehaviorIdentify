import java.sql.*;
import org . sqlite. JDBC; 
import java.util.List;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.*;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.*;
import org.jfree.data.xy.*;
import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.*;

public class user_behavior {
	public App app;
	public Time time;
	//public Time time_p;
	public Location location;
	public int location_tag;
	//public Location location_p;
	
	public boolean active_mode;
	public double speed;
	public int speed_level=0;
	
	public user_behavior previous_behavior;
	public user_behavior next_behavior;
	
	public sql_element this_element;//from a sql query;
	
	public double behavior_vector[]=new double[11];
	public int app_tag_vector[]=new int[18];
	
	public final static double EARTH_RADIUS=63781370;
	
	public user_behavior(long t1,int at_v[],int lt1,boolean am1,double sp){
		//app=new App(an1,at1);
		time=new Time(t1);
		//time_p=new Time(t2);
		//location=new Location(lg1,la1,lt1);
		//location_p=new Location(lg2,la2,lt2);
		location_tag = lt1;
		speed=sp;	
		speed_level=get_speed();
		active_mode=am1;
		//previous_behavior=get_previous_behavior(t2,an2,at2,lg2,la2,lt2,am2);
		
		
		//next_behavior=get_next_behavior();	
		for (int i=0;i<11;++i){
			behavior_vector[i]=0;
		}
		
		for (int i=0;i<18;++i){
			app_tag_vector[i]=at_v[i];
		}
	}
	
/*	
	public void user_behavior_build(String current_time){
		//this_element=exec sql select * from history where oid=current
		//this_element.oid=select oid from history where time=current;
		//this_element.time=select time from history where time=current;
		//this_element.app_name=select app_name from history where time=current;
		//this_element.app_tag=select app_tag from history where time=current;
		//this_element.location_tag=select location_tag from history where time=current;
		//this_element.longitude=select longitude from history where time=current;
		//this_element.latitude=select latitude from history where time=current;
		//this_element.active_mode=select active_mode from history where time=current;
		
		time.time=this_element.time;//time.time=exec sql select time from history where oid=current
		
		app.app_name=this_element.app_name;
		app.app_tag=this_element.app_tag;
		
		location.longitude=this_element.longitude;
		location.latitude=this_element.latitude;
		location.location_tag=this_element.location_tag;
		
		active_mode=this_element.active_mode;
		
		previous_behavior=get_previous_behavior(current_time);
		next_behavior=get_next_behavior(current_time);
		
		for (int i=0;i<11;++i){
			behavior_vector[i]=false;
		}
		
	}
*/
/*	
	public user_behavior get_previous_behavior(String t,String an, String at, double lg, double la, String lt,String am){
		//get previous_time :5 min ago
		sql_element previous_element;
		//previous_element.time=select time from history where oid=this_element.oid-1;
		//previous_element.oid=select oid from history where oid=this_element.oid-1;
		//previous_element.app_name=select app_name from history where oid=this_element.oid-1;
		//previous_element.app_tag=select app_tag from history where oid=this_element.oid-1;
		//previous_element.location_tag=select location_tag from history where oid=this_element.oid-1;
		//previous_element.longitude=select longitude from history where oid=this_element.oid-1;
		//previous_element.latitude=select latitude from history where oid=this_element.oid-1;
		user_behavior pb=new user_behavior(t,an,at,lg,la,lt,am);
		
		return pb;
	}
	*/
	/*
	public user_behavior get_next_behavior(String current_time){
		//get previous_time :5 minutes later
		sql_element next_element;
		//previous_element.time=select time from history where oid=this_element.oid-1;
		//previous_element.oid=select oid from history where oid=this_element.oid-1;
		//previous_element.app_name=select app_name from history where oid=this_element.oid-1;
		//previous_element.app_tag=select app_tag from history where oid=this_element.oid-1;
		//previous_element.location_tag=select location_tag from history where oid=this_element.oid-1;
		//previous_element.longitude=select longitude from history where oid=this_element.oid-1;
		//previous_element.latitude=select latitude from history where oid=this_element.oid-1;
		user_behavior nb=new user_behavior();;
		nb.time.time=this_element.time;//time.time=exec sql select time from history where oid=current
		
		nb.app.app_name=this_element.app_name;
		nb.app.app_tag=this_element.app_tag;
		
		nb.location.longitude=this_element.longitude;
		nb.location.latitude=this_element.latitude;
		nb.location.location_tag=this_element.location_tag;
		
		return nb;
	}
	*/
	
	public int time_interval(Time t1, Time t2){
		int delta_t;
		int a=t1.time.compareTo(t2.time);
		if (a<0){						//t1 is earlier
			delta_t=(t2.hour*3600+t2.min*60+t2.second)-(t1.hour*3600+t1.min*60+t1.second);
		}
		else if (a>0){					//t2 is earlier
			delta_t=(t1.hour*3600+t1.min*60+t1.second)-(t2.hour*3600+t2.min*60+t2.second);			
		}
		else {							//equal
			delta_t=0;
		}
		return delta_t;
	}
	
	private double rad(double d){
		return d*Math.PI/180.0;
	}
	
	public double get_distance(Location l1,Location l2){
		double radLat1 = rad(l1.latitude);
		double radLat2 = rad(l2.latitude);
		double a= radLat1 - radLat2;
		double b=rad(l1.longitude)-rad(l2.longitude);
		double disdance= 2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2), 2)+ Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2), 2)));
		disdance=disdance* EARTH_RADIUS;
		disdance=Math.round(disdance);
		
		return disdance;
	}
	
	public int get_speed(){
//		double distance=get_distance(location, location_p);
//		int dt=time_interval(time,time_p);
//		speed=distance/dt;		
	    if (speed>=1.0 && speed<1.5){//走路
			return 1;
		}
	    else if (speed<1.0) {
			return 0;//静止
			
		}
		else if (speed>=1.5 && speed<=4.0){//跑步
			return 2;
		}
		else if (speed>4.0 && speed<8.0){//骑车
			return 3;
		}
		else 
			return 4;
			
	}
	
	//user behavior identify function
	
	public void phone_identify(){	//通讯
		if (app_tag_vector[3]==1){ 
			behavior_vector[1]=1;	
		}
	}
	
	public void dinning_identify(){	//吃饭
		if (speed_level==0 && (time.time_period==3 || time.time_period==4 || time.time_period==5 || time.time_period==7 || time.time_period==8) && (location_tag==2 || location_tag==8 || location_tag==9 ) )
			{behavior_vector[2]=1;}
	}
	
	public void game_identify(){	//游戏
		if (app_tag_vector[9]==1)
			behavior_vector[3]=1;
	}
	
	public void moving_identify(){	//出行
		
		if (speed_level!=0)
			behavior_vector[4]=1;	
	}

	public void information_identify(){	//资讯
		if (app_tag_vector[8]==1 || app_tag_vector[14]==1)
			behavior_vector[5]=1;
	}
	
	public void sleep_identify(){	//睡觉
		if (speed_level==0 && time.time_period!=6 && time.time_period!=7 && time.time_period!=8 && (location_tag==3 || location_tag==4) )
			behavior_vector[6]=1;		
	}
	
	public void social_identify(){	//社交活动
		if (app_tag_vector[12]==1)
			behavior_vector[7]=1;
	}
	
	public  void exercise_identify(){	//运动
		if (speed_level==0 && location_tag==10)
			behavior_vector[8]=1;
		else if (speed_level==1 || speed_level==2)
			behavior_vector[8]=1;
	}
	
	public void working_identify(){	//工作学习
		
		if (speed_level==0 && (location_tag==1 || location_tag==5 || location_tag==6 || location_tag==7) && time.time_period!=2 && time.time_period!=3){
			behavior_vector[9]=1;
		}	
	}

	public void entertain_identify(){	//娱乐
		if (app_tag_vector[1]==1 || app_tag_vector[6]==1 || app_tag_vector[7]==1 || app_tag_vector[9]==1 || app_tag_vector[11]==1 || app_tag_vector[15]==1) 
			behavior_vector[10]=1;
	}

	/*
	public void unknown_identify(){		//未知行为
		if (active_mode=="unknown")
			behavior_vector[10]=true;
	}
	*/
	
	public static void main(String[] args){
		System.out.println("ab");
		
		int count=0;
		int count_record=0;
		int at_v[]=new int[18];
		int this_active;
		int this_tag;
		int location_tag=0;
		double speed=0;
		long time=0;
		boolean active=false;
		int tag_count[]=new int[18];
		int tag_every_count[]=new int[18];
		int real_activity[]=new int[11];
		int count_activity[]=new int[11];
		double pre_behavior_v[]=new double[11];
		int this_activity=0;		//current activity get from database
		for (int i=0;i<11;++i){
			real_activity[i]=0;
			count_activity[i]=0;
			pre_behavior_v[i]=0;
		}

		int oid=1;
		int a_i_oid=1;
		
		//create timeseries
		TimeSeriesCollection timeseriescollection= new TimeSeriesCollection();
		TimeSeries timeseries3=new TimeSeries("time line for communication",Second.class);
		TimeSeries timeseries7=new TimeSeries("time line for media",Second.class);
		TimeSeries timeseries17=new TimeSeries("time line for game",Second.class);
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/mobile_record";
		
		String user = "root";
		String password = "1111";
		
		try{
			//Class.forName(driver);
			Class.forName("org.sqlite.JDBC");
			
			Connection connection_Installed = DriverManager.getConnection("jdbc:sqlite:/c:/Users/hfjingj/Desktop/Installed");
			Connection connection_record = DriverManager.getConnection("jdbc:sqlite:/c:/Users/hfjingj/Desktop/record");
			Statement stmt_installed = connection_Installed.createStatement();
			Statement stmt_record = connection_record.createStatement();
			
			if(!connection_Installed.isClosed())
				System.out.println("Succeeded connecting to Installed!");
			if(!connection_record.isClosed())
				System.out.println("Succeeded connecting to record!");

				String sql = "create table activity_identify(id integer primary key autoincrement, time long, act_0 float, act_1 float, act_2 float, act_3 float, act_4 float, act_5 float, act_6 float, act_7 float, act_8 float, act_9 float, act_10 float);";
				stmt_record.executeUpdate(sql);
				
				
				sql = "select count(*) from installed;";
				ResultSet rs = stmt_installed.executeQuery(sql);
				while (rs.next()){
					count=rs.getInt(1);					
					//count= Integer.parseInt(result);
					System.out.println(count);
				}
				
				//statement = conn.createStatement();
				
				sql= "select count(*) from record;";
				rs = stmt_record.executeQuery(sql);
				while (rs.next()){
					count_record=rs.getInt(1);
					System.out.println(count_record+"*");
				}
				
				for (int j=0;j<count_record;++j){
					
					//initialize
					for (int i=0;i<18;++i){
						at_v[i]=0;
					}
					this_active=0;
					location_tag=0;
					speed=0;
					time=0;
					active=false;
					this_activity=0;
					
				//get data from database
				sql = "select * from record where id="+oid+";";
				rs = stmt_record.executeQuery(sql);
				while (rs.next()){
					this_activity=rs.getInt("activity");
				}
				
				real_activity[this_activity]++;
				
				//Statement statement_get_t = conn.createStatement();
				sql = "select * from record where id="+oid+";";
				rs = stmt_record.executeQuery(sql);
				while (rs.next()){
					time = rs.getLong("time");
				}
				
				
				//Statement statement_get_l = conn.createStatement();
				sql = "select * from record where id="+oid+";";
				rs = stmt_record.executeQuery(sql);
				while (rs.next()){
					location_tag = rs.getInt("location");
				}
				
				//Statement statement_get_sp = conn.createStatement();
				sql = "select * from record where id="+oid+";";
				rs = stmt_record.executeQuery(sql);
				while (rs.next()){
					speed = rs.getDouble("speed");
				}
				
				for (int k=0;k<18;++k){
					tag_every_count[k]=0;
				}				
				
				for (int i=0;i<count;++i){

					//Statement statement2 = conn.createStatement();
					this_active=0;
					sql="select * from record where id="+oid+";";
					rs = stmt_record.executeQuery(sql);
					while(rs.next()){
					this_active = rs.getInt("process_"+(i+1)+"_active");
					if (this_active==0){
						this_active=0;
					}
					else if (this_active==1){
						this_active=1;
					}
					else
						this_active=0;
					}
					//System.out.println((i+1)+":"+this_tag);
					if (this_active==1){
						//System.out.println(i+1+"a");
						//Statement statement3 = conn.createStatement();
						sql="select * from installed where id="+(i+1)+";";
						rs = stmt_installed.executeQuery(sql);
						while (rs.next()){
							this_tag = rs.getInt("tag");
							tag_count[this_tag]++;
							tag_every_count[this_tag]++;
							at_v[this_tag]=1;						
						}
					}

				}
				
				//active_identify
				for (int i=0;i<18;i++){
					if (at_v[i]==1){
						active=true;
						break;
					}			
				}
				//user_behavior build
				user_behavior line=new user_behavior(time,at_v,location_tag, active, speed);
				Second second=new Second(line.time.second,new Minute(line.time.min,new Hour(line.time.hour, new Day(line.time.date,7,2014))));
				line.phone_identify();
				line.information_identify();
				line.game_identify();
				line.dinning_identify();
				line.working_identify();
				line.social_identify();
				
				line.exercise_identify();
				line.sleep_identify();
				line.moving_identify();
				line.entertain_identify();
				//line.unknown_identify();
				
				timeseries3.add(second,tag_every_count[3]);
				timeseries7.add(second,tag_every_count[7]);
				timeseries17.add(second,tag_every_count[17]);
				
				System.out.println("*************"+oid+"******************");
				System.out.println(tag_every_count[3]);
				System.out.println(tag_every_count[7]);
				System.out.println(tag_every_count[17]);
				for (int i=0;i<11;++i){
					if (line.behavior_vector[i]==1){
						count_activity[i]++;
					}
				}
				
				//test out:put your test code here
				//add at_v into database
				boolean flag=false;
				for (int i=0;i<11;++i){
					if (line.behavior_vector[i]!=pre_behavior_v[i]){
						flag=true;
					}
				}
				if (flag){
					sql = "insert into activity_identify values("+a_i_oid+","+line.time.int_time+","+(double)line.behavior_vector[0]+","+(double)line.behavior_vector[1]+","+(double)line.behavior_vector[2]+","+(double)line.behavior_vector[3]+","+(double)line.behavior_vector[4]+","+(double)line.behavior_vector[5]+","+(double)line.behavior_vector[6]+","+(double)line.behavior_vector[7]+","+(double)line.behavior_vector[8]+","+(double)line.behavior_vector[9]+","+(double)line.behavior_vector[10]+");";
					stmt_record.executeUpdate(sql);
					a_i_oid++;
					for (int i=0;i<11;++i){
						pre_behavior_v[i]=line.behavior_vector[i];
					}
				}
				
				oid+=1;
				}
				rs.close();
				connection_Installed.close();
				connection_record.close();
		}catch(ClassNotFoundException e) {   
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
			}catch(SQLException e) {   
				e.printStackTrace();   
				} catch(Exception e) { 
					e.printStackTrace();   
					}  

		//calculate hit rate
		for (int i=0;i<11;i++){
			System.out.println(i+":"+count_activity[i]+"    "+real_activity[i]);
		}
			
		
		
		double hit_rate=0;
		for (int i=0;i<11;++i){
			if (real_activity[i]==0){
				System.out.println(i+":null");
			}
			else{
				hit_rate=(double)count_activity[i]/real_activity[i];
				System.out.println(i+":"+hit_rate);
			}
			
		
		}
		
		for (int i=0;i<18;++i){
			System.out.println(i+"     "+tag_count[i]);
		}
		
		DefaultPieDataset dpd=new DefaultPieDataset();
		//for (int i=0;i<18;++i){
		//	dpd.setValue(""+i, tag_count[i]);
		//}
		dpd.setValue("beautification", tag_count[1]);
		dpd.setValue("business", tag_count[2]);
		dpd.setValue("communication", tag_count[3]);
		dpd.setValue("education", tag_count[4]);
		dpd.setValue("finance", tag_count[5]);
		dpd.setValue("lifestyle", tag_count[6]);
		dpd.setValue("media", tag_count[7]);
		dpd.setValue("information", tag_count[8]);
		dpd.setValue("shopping", tag_count[9]);
		dpd.setValue("optimization", tag_count[10]);
		dpd.setValue("reading", tag_count[11]);
		dpd.setValue("social_network", tag_count[12]);
		dpd.setValue("system", tag_count[13]);
		dpd.setValue("tool", tag_count[14]);
		dpd.setValue("travel", tag_count[15]);
		dpd.setValue("ignore", tag_count[16]);
		dpd.setValue("game", tag_count[17]);
		
		JFreeChart chart=ChartFactory.createPieChart("各种类型app使用分布", dpd, true, true, false);
		ChartFrame chartFrame=new ChartFrame("各种类型app使用分布",chart);
		chartFrame.pack();
		chartFrame.setVisible(true);
		
		
		timeseriescollection.addSeries(timeseries3);
		timeseriescollection.addSeries(timeseries7);		
		timeseriescollection.addSeries(timeseries17);	
		XYDataset xydataset=timeseriescollection;
		JFreeChart jfreechart2 = ChartFactory.createTimeSeriesChart("app tag的时间分布情况", "time", "使用次数", xydataset, true, true, true);
		ChartFrame frame2= new ChartFrame("app tag时间分布情况",jfreechart2);
		frame2.pack();
		
		XYPlot xyplot=(XYPlot) jfreechart2.getPlot();
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
		ValueAxis rangeAxis=xyplot.getRangeAxis();
		frame2.setVisible(true);
		
			
	}
	
}
