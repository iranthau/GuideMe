//Written by Iran R

package com.example.GuideMe;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private BufferedWriter bfWriter = null;

	Timer updateTimer = null;
	Timer writeTimer = null;
	private Calendar c = null;

	//Temporary variables linear accelerometer for sensor values
	float tempLAx = 0;
	float tempLAy = 0;
	float tempLAz = 0;

	//Temporary variables for accelerometer sensor values
	float tempAx = 0;
	float tempAy = 0;
	float tempAz = 0;

	float tempGx = 0;
	float tempGy = 0;
	float tempGz = 0;

	float tempMFx = 0;
	float tempMFy = 0;
	float tempMFz = 0;

	float tempOx = 0;
	float tempOy = 0;
	float tempOz = 0;

	float tempTc = 0;

	float tempPh = 0;

	float tempPr = 0;

	float tempLi = 0;

	double tempLong = 0;

	double tempLat = 0;

	double tempAcc = 0;

	double tempAlt = 0;

	long dataCaptureStart;

	int readerCounter = 0;
	int writerCounter = 0;
	int fileIndex = 0;
	boolean recording = false;
	boolean recordCompleted = false;

	// Sample rate for data capture in ms
	int readInt = 20;
	int writeInt = 60000;

	CopyOnWriteArrayList<SensorData> dataCollection = new CopyOnWriteArrayList<SensorData>();

	SensorManager sensorManager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		try {
			Button button = (Button) findViewById(R.id.button_capture);
			button.setText("START");
			
			initialiseSensors();
		} catch (Exception ex) {
		}
	}

	//Menu options
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return super.onCreateOptionsMenu(menu);		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			
			case R.id.aboutGuideMe:
				showAboutGuideMe(findViewById(R.id.aboutGuideMe));
				return true;
			case R.id.guide:
				showGuide(findViewById(R.id.guide));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	// Always call the superclass method first
	@Override
	public void onResume() {
		super.onResume();

	}

	// Always call the superclass method first
	@Override
	public void onRestart() {
		super.onRestart();
	}

	public void initializeParameters() {

		// c = null;

		// Temporary variables for sensor values
		tempLAx = 0;
		tempLAy = 0;
		tempLAz = 0;

		// Temporary variables for sensor values
		tempAx = 0;
		tempAy = 0;
		tempAz = 0;

		tempGx = 0;
		tempGy = 0;
		tempGz = 0;

		tempMFx = 0;
		tempMFy = 0;
		tempMFz = 0;

		tempOx = 0;
		tempOy = 0;
		tempOz = 0;

		tempTc = 0;
		tempPh = 0;
		tempPr = 0;
		tempLi = 0;
		tempLong = 0;
		tempLat = 0;
		tempAcc = 0;
		tempAlt = 0;

	}

	public void initialiseSensors() {

		// Do something in response to button click
		// Define sensor manager and sensors
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		final Sensor laSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		final Sensor aSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		final Sensor gSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		final Sensor mfSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		@SuppressWarnings("deprecation")
		final Sensor oSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		@SuppressWarnings("deprecation")
		final Sensor tSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
		final Sensor pSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_PRESSURE);
		final Sensor prSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		final Sensor lSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_LIGHT);

		sensorManager.registerListener(myLinearAccelerometerListener, laSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myAccelerometerListener, aSensor,
				SensorManager.SENSOR_DELAY_FASTEST);

		// by registering sensor manager and defining sensors
		sensorManager.registerListener(myGyroscopeListener, gSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myCompassListener, mfSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myOrientationListener, oSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myTemperatureListener, tSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myPressureListener, pSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myProximityListener, prSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(myLightListener, lSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void releaseSensors() {

	}

	/** Nullify everything on program exit to clear memory. */
	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager = null;

	};

	public void captureData(View v) {
		try {
			
			Button btnRecording = (Button) findViewById(R.id.button_capture);
			TextView textView = (TextView) findViewById(R.id.textView1);
			TextView label = (TextView) findViewById(R.id.label);
			
			if (recording == false) {
				btnRecording.setText("STOP");
				
				textView.setText("Capturing Data...");
				label.setText("");
				btnRecording.setBackgroundResource(R.drawable.button_pressed);

				readerCounter = 0;
				writerCounter = 0;
				fileIndex = 0;

				recording = true;

				dataCaptureStart = System.currentTimeMillis();
				c = Calendar.getInstance();
				startCapturing();

				String fleName = getFilename() + "Sensor_" + getTime() + "_"
						+ fileIndex + ".csv";
				initSensorDataFile(fleName);
				startWriting();
			} else {
				updateTimer.cancel();
				writeTimer.cancel();
				stopCapturing();
				btnRecording.setText(" Finalising....");
				writeSensorDataToFile(writerCounter, true);	
				
				if(recordCompleted == true) {
					btnRecording.setText("START");
					textView.setText("");
					
					label.setText("Data saved to /Storage/emulated/0/GuideMeFiles");
				}
				
				recording = false;
				initializeParameters();

				initialiseSensors();
			}

		} catch (Exception ex) {
		}
	}

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath()
				+ "/" + "GuideMeFiles" + "/" + getDate();
		
		File file = new File(filepath);
		
		if (!file.exists()) {
			file.mkdirs();
		}
		return (file.getAbsolutePath() + "/");
	}

	public void startCapturing() {
		updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// Start capturing if all sensors are ready
				SensorData data = new SensorData();
				data.linearAccelerometerX = tempLAx; 
				
				// Store sensor data values into arrays
				data.linearAccelerometerY = tempLAy;
				data.linearAccelerometerZ = tempLAz;
				data.accelerometerX = tempAx; 
				
				// Store sensor data values into arrays
				data.accelerometerY = tempAy;
				data.accelerometerZ = tempAz;
				data.Gx = tempGx;
				data.Gy = tempGy;
				data.Gz = tempGz;
				data.MFx = tempMFx;
				data.MFy = tempMFy;
				data.MFz = tempMFz;
				data.Ox = tempOx;
				data.Oy = tempOy;
				data.Oz = tempOz;
				data.Tc = tempTc;
				data.pressure = tempPh;
				data.Pr = tempPr;
				data.Li = tempLi;

				data.time = System.currentTimeMillis();
				dataCollection.add(data);
				
				// Increment array position
				readerCounter = readerCounter + 1; 
			}
		}, 0, readInt);
	}

	public void startWriting() {
		writeTimer = new Timer();
		writeTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {

				if (writerCounter != 0) {
					writerTask task = new writerTask();
					task.execute(Integer.toString(writerCounter));
				}
				// Increment array position
				writerCounter = writerCounter + 1; 
			}
		}, 0, writeInt);
	}

	private class writerTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			int index = Integer.parseInt(params[0]);
			Log.w("Executed -", params[0]);
			writeSensorDataToFile(index, false);
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	public void stopCapturing() {
		sensorManager.unregisterListener(myLinearAccelerometerListener);
		sensorManager.unregisterListener(myAccelerometerListener);
		sensorManager.unregisterListener(myGyroscopeListener);
		sensorManager.unregisterListener(myCompassListener);
		sensorManager.unregisterListener(myOrientationListener);
		sensorManager.unregisterListener(myTemperatureListener);
		sensorManager.unregisterListener(myPressureListener);
		sensorManager.unregisterListener(myProximityListener);
		sensorManager.unregisterListener(myLightListener);
		updateTimer.cancel();
		writeTimer.cancel();
	}

	// This method over-ride the back button so the user can not exit.
	// Code added by IranR
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
		}

		return false;
	}

	// End of added code

	private void writeSensorDataToFile(int index, boolean isExist) {
		try { // Saves data to a file

			StringBuilder writer = new StringBuilder();

			/** Write values of sensor data file. */
			for (int j = 0; j < dataCollection.size(); j++) {
				SensorData data = dataCollection.get(j);
				String lineI = (Long.toString(data.time) + ","
						+ data.linearAccelerometerX + ","
						+ data.linearAccelerometerY + ","
						+ data.linearAccelerometerZ + "," + data.accelerometerX
						+ "," + data.accelerometerY + "," + data.accelerometerZ
						+ "," + data.Gx + "," + data.Gy + "," + data.Gz + ","
						+ data.Ox + "," + data.Oy + "," + data.Oz + ","
						+ data.MFx + "," + data.MFy + "," + data.MFz + "," + data.pressure);
				writer.append(lineI);
				writer.append('\n');
			}

			/** Complete the sensor data file. */
			dataCollection = new CopyOnWriteArrayList<SensorData>();
			bfWriter.write(writer.toString());
			bfWriter.flush();

			int remainder = index % 3;

			if (remainder == 0) {
				bfWriter.close();
				fileIndex++;
				String fileName = getFilename() + "Sensor_" + getTime() + "_"
						+ fileIndex + ".csv";
				initSensorDataFile(fileName);

			}
			
			Log.w("Write Completed -", Integer.toString(index));
			
			recordCompleted = true;

			if (isExist) {
				bfWriter.close();
				
				// Enable the button
				Button btnRecording = (Button) findViewById(R.id.button_capture);
				btnRecording.setBackgroundResource(R.drawable.button_default);
				btnRecording.setEnabled(true);
			}
		} catch (Exception e) {
			Log.w("Write Error -", e.getMessage());
		}
	}

	private void initSensorDataFile(String fileName) {
		try {

			Log.w("Create File -", fileName);
			File file = new File(fileName);
			bfWriter = new BufferedWriter(new FileWriter(file));

			StringBuilder writer = new StringBuilder();

			writer.append("Time (ms)");
			writer.append(',');
			writer.append("LAx (m/s^2)");
			writer.append(',');
			writer.append("LAy (m/s^2)");
			writer.append(',');
			writer.append("LAz (m/s^2)");
			writer.append(',');
			writer.append("Ax (m/s^2)");
			writer.append(',');
			writer.append("Ay (m/s^2)");
			writer.append(',');
			writer.append("Az (m/s^2)");
			writer.append(',');
			writer.append("Gx (rad/s)");
			writer.append(',');
			writer.append("Gy (rad/s)");
			writer.append(',');
			writer.append("Gz (rad/s)");
			writer.append(',');
			writer.append("Ox (deg)");
			writer.append(',');
			writer.append("Oy (deg)");
			writer.append(',');
			writer.append("Oz (deg)");
			writer.append(',');
			writer.append("Mx (uT)");
			writer.append(',');
			writer.append("My (uT)");
			writer.append(',');
			writer.append("Mz (uT)");
			writer.append(',');
			writer.append("Pressure");
			writer.append('\n');

			bfWriter.write(writer.toString());
			bfWriter.flush();

		} catch (Exception ex) {
			Log.w("Write Error -", ex.getMessage());
		}
	}

	private String getTime() {
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH) + 1;
		int mDay = c.get(Calendar.DATE);
		int mHour = c.get(Calendar.HOUR_OF_DAY);
		int mMinute = c.get(Calendar.MINUTE);
		int mSecond = c.get(Calendar.SECOND);
		int mMiliSecond = c.get(Calendar.MILLISECOND);

		// Build the file name using date and time
		return (mYear + "_" + mMonth + "_" + mDay + "_" + mHour + "_" + mMinute
				+ "_" + mSecond + "_" + mMiliSecond); 

	}

	private String getDate() {
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH) + 1;
		int mDay = c.get(Calendar.DATE);

		// Build the file name using date and time
		return (mYear + "_" + mMonth + "_" + mDay); 

	}
	
	//About guide me.
	public void showAboutGuideMe(View view) {
		
		Intent aboutIntent = new Intent(MainActivity.this, DisplayAboutGuideMeActivity.class);
		startActivity(aboutIntent);
	}
	
	public void showGuide(View view) {
		Intent guideIntent = new Intent(MainActivity.this, DisplayGuideActivity.class);
		startActivity(guideIntent);
	}

	final SensorEventListener myLinearAccelerometerListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

				tempLAx = sensorEvent.values[0];
				tempLAy = sensorEvent.values[1];
				tempLAz = sensorEvent.values[2];

			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	final SensorEventListener myAccelerometerListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

				tempAx = sensorEvent.values[0];
				tempAy = sensorEvent.values[1];
				tempAz = sensorEvent.values[2];
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	/** Gyroscope Sensor Listener */
	final SensorEventListener myGyroscopeListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
				tempGx = sensorEvent.values[0];
				tempGy = sensorEvent.values[1];
				tempGz = sensorEvent.values[2];

			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	/** Magnetic Field Sensor Listener */
	final SensorEventListener myCompassListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				tempMFx = sensorEvent.values[0];
				tempMFy = sensorEvent.values[1];
				tempMFz = sensorEvent.values[2];

			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	/** Accelerometer Sensor Listener */
	final SensorEventListener myOrientationListener = new SensorEventListener() {
		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				tempOx = sensorEvent.values[0];
				tempOy = sensorEvent.values[1];
				tempOz = sensorEvent.values[2];			 
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	final SensorEventListener myTemperatureListener = new SensorEventListener() {
		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_TEMPERATURE) {
				tempTc = sensorEvent.values[0];
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	final SensorEventListener myPressureListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
				tempPh = sensorEvent.values[0];
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	final SensorEventListener myProximityListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
				tempPr = sensorEvent.values[0];
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	final SensorEventListener myLightListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_PRESSURE) {
				tempLi = sensorEvent.values[0];
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
}
