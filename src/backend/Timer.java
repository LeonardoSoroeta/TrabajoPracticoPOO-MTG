package backend;

import java.math.BigDecimal;

// vamos a necesitar esta clase para desarrollar el UI
public class Timer {
	private float time;
	private float last;
	public Timer(){
		time = 0;
	}

	public void update(float deltaTime){
		time+= deltaTime;
	}
	
	public void reset(){
		time = 0;
		last = 0;
	}

	public float getTime(){
		return round(time,2); 
	}


	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}


	public float getDeltaTime(){
		float aux =  getTime() - last;
		last = getTime();
		return aux;
	}
	
}