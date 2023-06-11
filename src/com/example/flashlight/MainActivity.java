package com.example.flashlight;


import ir.adad.client.Adad;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import co.ronash.pushe.Pushe;
public class MainActivity extends Activity {
 
	
	ImageButton btnSwitch,btnsound1,about2,setting1,imnazar,imexit;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash,playsound;
    Parameters params;
    MediaPlayer mp;
    Boolean flash,pouse,bl_et_visible;
    TextView tv;
    int select1;
   long endvalue;
    int selectedId;
	long remindetime;
    Handler handler;
    CountDownTimer timer;
    SharedPreferences pref;
    Editor editor;
    Runnable myRunnable;
   int seconds , minutes;
   Button btn_time;
   TextView tv_time;
   EditText et_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adad.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        Pushe.initialize(this, true);
        tv=(TextView)findViewById(R.id.textView1);
         pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); 
         editor = pref.edit();
         playsound=true;   
       handler = new Handler();
	  setting1 = (ImageButton) findViewById(R.id.btnsetting);
        btnSwitch = (ImageButton) findViewById(R.id.btnSwitch);
        imexit = (ImageButton) findViewById(R.id.btnclose);
        about2 = (ImageButton) findViewById(R.id.btnabout);
        imnazar = (ImageButton) findViewById(R.id.btncomment);
        btnsound1 = (ImageButton) findViewById(R.id.btnsound);
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
         
        if (!hasFlash) {
        	       	
        }
       
       
        btnsound1.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View v) {
                if (playsound) {
                   playsound=false;
                   btnsound1.setImageResource(R.drawable.mute);
                } else {
                	playsound=true;
                    btnsound1.setImageResource(R.drawable.unmute);
                }
            }
        });
       setting1.setOnClickListener(new View.OnClickListener() {
          	 
            @Override
            public void onClick(View v) {
            	/*final Dialog dialog3 = new Dialog(MainActivity.this);
		         dialog3.setContentView(R.layout.setting);
		         dialog3.setTitle("Setting");
		         dialog3.setCancelable(true);*/
            	final Dialog dialog3 =new Dialog(MainActivity.this);
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DisplayMetrics displaymetrics = new DisplayMetrics();
                MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                WindowManager.LayoutParams lp = dialog3.getWindow().getAttributes();
                lp.dimAmount = 0.7f;
                lp.width=LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height= LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity= Gravity.CENTER;
                dialog3.getWindow().setAttributes(lp);
                dialog3.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog3.setContentView(R.layout.setting);
		         
		          int time=pref.getInt("time2",5);
		          
		          bl_et_visible=false;
		          et_time=(EditText)dialog3.findViewById(R.id.et_time);
		          btn_time=(Button)dialog3.findViewById(R.id.btn_edit_custom_time);
		          tv_time=(TextView)dialog3.findViewById(R.id.tv_time);
		         TextView tv_ok=(TextView)dialog3.findViewById(R.id.setting_ok);
		         TextView tv_cancel=(TextView)dialog3.findViewById(R.id.setting_cancel);
		          
		          RadioButton five=(RadioButton) dialog3.findViewById(R.id.five);
		          RadioButton ten=(RadioButton) dialog3.findViewById(R.id.ten);
		          RadioButton thirty=(RadioButton) dialog3.findViewById(R.id.thirty);
		          RadioButton sixty=(RadioButton) dialog3.findViewById(R.id.sixty);
		          final RadioButton   rb_custom_time=(RadioButton) dialog3.findViewById(R.id.custom_time);
		          
		          int custom_time1=pref.getInt("custom_time",0);
		          if (custom_time1>0){
		        	  rb_custom_time.setText(custom_time1+" دقیقه ");
		        	  rb_custom_time.setEnabled(true);
		          }
		          btn_time.setOnClickListener(new OnClickListener() {
		  			
		  			@Override
		  			public void onClick(View v) {
		  				if(!bl_et_visible){
		  					btn_time.setText("ذخیره");
		  				et_time.setVisibility(View.VISIBLE);
		  				tv_time.setVisibility(View.VISIBLE);
		  				bl_et_visible=true;
		  				}else{
		  					btn_time.setText("ویرایش زمان دلخواه");
		  					et_time.setVisibility(View.GONE);
		  					tv_time.setVisibility(View.GONE);
		  					bl_et_visible=false;
		  					if(et_time.getText().toString().length()>0){
		  					int value=Integer.parseInt(et_time.getText().toString());
		  					if(value>0){
		  					editor.putInt("custom_time",value); 
		  					editor.commit();
		  					rb_custom_time.setEnabled(true); 
		  					rb_custom_time.setText(value+" دقیقه ");
		  					Toast.makeText(MainActivity.this, "زمان دلخواه شما ذخیره شد", Toast.LENGTH_LONG).show();
		  					}
		  					}
		  				}
		  			}
		  		});
		          
		          if((time>0)&&(time!=5)&&(time!=10)&&(time!=30)&&(time!=60)){
		        	 rb_custom_time.setChecked(true);
		        	 rb_custom_time.setEnabled(true);
		          }else
		          switch( time)
				    {
				       case 5: 
				    	five.setChecked(true);
				    	      break;
				       case 10: 
				    	   ten.setChecked(true);
				    	       break;
				       case 30:   
				    	   thirty.setChecked(true);
				    	       break;
				       case 60: 
				    	   sixty.setChecked(true);
				                break;
				    }
		          RadioGroup radioGroup = (RadioGroup) dialog3.findViewById(R.id.group1);
		        
		          radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		         {
		             @Override
		             public void onCheckedChanged(RadioGroup group, int checkedId) {
		            	 selectedId = checkedId;
		              }
		         });
		          
		          
		         tv_cancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dialog3.cancel();
							
						}
					});
		         
		         tv_ok.setOnClickListener(new OnClickListener() {
		        	 int value;
						@Override
						public void onClick(View arg0) {
							
							switch( selectedId )
						    {
						       case R.id.five: 
						    	value=5;
						    	      break;
						       case R.id.ten: 
						    	   value=10;
						    	       break;
						       case R.id.thirty:   
						    	   value=30;
						    	       break;
						       case R.id.sixty: 
						    	   value=60;
						                break;
						       case R.id.custom_time:
						           int custom_time2=pref.getInt("custom_time",1);
						    	   value=custom_time2;
						    	   break;
						    }
							editor.putInt("time2",value); 
							editor.commit();
						    dialog3.cancel();
						    //Log.d("time2",value+"");
									}
					});
		       
		         dialog3.show();
            }
        });       
       about2.setOnClickListener(new View.OnClickListener() {
          	 @Override
            public void onClick(View v) {
              
            	/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            	alertDialog.setTitle(R.string.about1);
				alertDialog.setMessage(R.string.about3);
				alertDialog.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});*/
          		final Dialog dialog3 =new Dialog(MainActivity.this);
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DisplayMetrics displaymetrics = new DisplayMetrics();
                MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                WindowManager.LayoutParams lp = dialog3.getWindow().getAttributes();
                lp.dimAmount = 0.7f;
                lp.width=LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height= LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity= Gravity.CENTER;
                dialog3.getWindow().setAttributes(lp);
                dialog3.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog3.setContentView(R.layout.about);
                TextView tv_exit=(TextView)dialog3.findViewById(R.id.about_exit);
		         tv_exit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {

						dialog3.cancel();
					}
				});
				dialog3.show();
		    	 
            }
        });
      
        btnSwitch.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
            	 handler.removeCallbacks(myRunnable);
             
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                    tv.setText("00:00");
                    timer.cancel();
                                       
                } else {
                    // turn on flash
                    turnOnFlash(); 
                     endvalue =  pref.getInt("time2",5)*60*1000;
                    sethandler();
                    settimer(endvalue);
                }
            }
        });
        
imexit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						MainActivity.this);
						
						alertDialog.setTitle(R.string.exit);
						alertDialog.setMessage(R.string.mayel);

						
						alertDialog.setPositiveButton(R.string.yes2,
						new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						finish();
						}
						});
						alertDialog.setNegativeButton(R.string.no2,
						new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();
						}
						});
					
						alertDialog.show();*/
				final Dialog dialog3 =new Dialog(MainActivity.this);
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DisplayMetrics displaymetrics = new DisplayMetrics();
                MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                WindowManager.LayoutParams lp = dialog3.getWindow().getAttributes();
                lp.dimAmount = 0.7f;
                lp.width=LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height= LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity= Gravity.CENTER;
                dialog3.getWindow().setAttributes(lp);
                dialog3.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog3.setContentView(R.layout.exit);
                
                TextView tv_ok=(TextView)dialog3.findViewById(R.id.exit_ok);
		        TextView tv_cancel=(TextView)dialog3.findViewById(R.id.exit_cancel);
		        TextView tv_comment=(TextView)dialog3.findViewById(R.id.exit_comment);
		        
		        tv_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {

						finish();
					}
				});
				
		        tv_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {

						dialog3.cancel();
					}
				});
		        
		        tv_comment.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {

						Intent intent = new Intent(Intent.ACTION_EDIT); 
						intent.setData(Uri.parse("bazaar://details?id=" +  "com.example.flashlight")); 
						intent.setPackage("com.farsitel.bazaar"); 
						startActivity(intent); 
						
					}
				});
		        dialog3.show();
			}
			
		});
        
        imnazar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_EDIT); 
				intent.setData(Uri.parse("bazaar://details?id=" +  "com.example.flashlight")); 
				intent.setPackage("com.farsitel.bazaar"); 
				startActivity(intent); 
				
			}
		}) ;
        
  
        flash=true;
        isFlashOn=false;
       /* editor.putInt("time",5); 
		editor.commit();*/
         
}
    private void sethandler() {
		// TODO Auto-generated method stub
    	int time=pref.getInt("time2", 5);
    	time=time*60*1000;
    	 myRunnable = new Runnable() {
             @Override
   		  public void run() {
         		if (isFlashOn) {
         		turnOffFlash();
         		tv.setText("00:00");
         		if (timer!=null)
         			timer.cancel();
         		
					}
   		  		  }
         };
         handler.postDelayed(myRunnable, time);

	}
    
    private void settimer(long remindetime2) {
		// TODO Auto-generated method stub
    	  
        
         if (isFlashOn) {
        	 if(timer!=null)
        	        timer.cancel();
        	 timer=new CountDownTimer(remindetime2, 1000) {

                 public void onTick(long millisUntilFinished) {
                	 long rm=millisUntilFinished % 60000;
                	 long m=millisUntilFinished /60000;
                	 long s=rm / 1000;
                 //    tv.setText("زمان باقیمانده: " + millisUntilFinished / 1000 +" دقیقه");
                     tv.setText( m + " : " + s );
                     remindetime=millisUntilFinished;
                    //here you can have your logic to set text to edittext
                 }

                 public void onFinish() {
                	 timer.cancel();
                     tv.setText("00:00");
                 }

             }.start();
		} 
         
	}
   
    private void playSound(){
    	
    	if(playsound){
        if(isFlashOn){
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_off);
        }else{
            mp = MediaPlayer.create(MainActivity.this, R.raw.light_switch_on);
        }
        mp.setOnCompletionListener(new OnCompletionListener() {
     
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        }); 
        mp.start();
    	}
    }
    
    private void toggleButtonImage(){
        if(isFlashOn){
            btnSwitch.setImageResource(R.drawable.btn_switch_on);
        }else{
            btnSwitch.setImageResource(R.drawable.btn_switch_off);
        }
    }
    
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }
     
     /*
     * Turning On flash
     */
    private void turnOnFlash() {
    	
    	if (hasFlash){
    	
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
       
            playSound();
             
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
             
            // changing button/switch image
            toggleButtonImage();
        }
        
    	}
    	
    	else {
    		playSound();
    		isFlashOn = true;
    		toggleButtonImage();
		}
     
    }
    
    private void turnOffFlash() {
    	
    	if (hasFlash){
    		
    	
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();
             
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
             
            // changing button/switch image
            toggleButtonImage();
        }
    	}
    	else {
    		playSound();
    		isFlashOn = false;
    		toggleButtonImage();
		}
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if(timer!=null)
            timer.cancel();
        handler.removeCallbacks(myRunnable);
    }
     
    @Override
    protected void onPause() {
        super.onPause();      
       // on pause turn off the flash
    //    turnOffFlash();
      editor.putBoolean("flash",isFlashOn);
        if (isFlashOn){
        	long oldtime=System.currentTimeMillis();
        	editor.putLong("oldtime",oldtime);
			editor.putLong("resume",remindetime);}
       
        editor.commit();
        if(timer!=null)
            timer.cancel();
         }
     
    @Override
    protected void onRestart() {
        super.onRestart();
    }
     
    @Override
    protected void onResume() {
        super.onResume();     
        Boolean oldflash = pref.getBoolean("flash",false);
        // on resume turn on the flash
        if(hasFlash){
            if (flash) {
            	turnOnFlash();
            	flash=false;
            sethandler();
            remindetime=pref.getInt("time2", 5)*60*1000;
            settimer(remindetime);}
            
             else{
            	 if (oldflash) {
					long oldtime=pref.getLong("oldtime",0);
            long ctime=System.currentTimeMillis();
            long resume=pref.getLong("resume", 10000);
            long rtime2=resume-(ctime-oldtime);
             if (rtime2>2000) {
            	remindetime=rtime2;
            	settimer(remindetime);
			}
				}
			
            }
			}
         
           }
     
    @Override
    protected void onStart() {
        super.onStart();
         
        // on starting the app get the camera params
        getCamera();
          }
     
    @Override
    protected void onStop() {
        super.onStop();
      
        
        if(timer!=null)
        timer.cancel();
        // on stop release the camera
      /*  if (camera != null) {
            camera.release();
            camera = null;
        }*/
        
    }
}