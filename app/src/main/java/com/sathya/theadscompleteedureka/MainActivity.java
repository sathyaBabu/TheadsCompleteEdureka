package com.sathya.theadscompleteedureka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    MyWifi myWifi;
    MyView myView ;

    TextView textView ;
    private ProgressBar progressBar1;
    // UI Handler...
    // This handler is a pointer into the UI handler..( Green Patch ) 16ms
    Handler handler = new Handler();
    Handler progressBarHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView) ;
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar) ;

       // this AppCompatActivity   this is related to view xml files..

        // wrap it up with a thread !!!!

        // My Advise is to move the code into a service...

        // When the orientation changes : THRAED WILL DIE...

        // UI thread( Android ms it will not be 1/4 ms )  worker thread 1/4ms



//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//
//                                for(int i = 0 ; i<= 30000;i++){
//                                    for(int ii = 0 ; ii<= 30000;ii++){
//
//                                        // nx2 BUbble sort...
//
//                                    }
//
//                                    Log.d("tag"," in i "+i);
//                                }
//
//                    }
//                }
//
//
//        ).start();


//         for(int i = 0 ; i<= 30000;i++){
//             for(int ii = 0 ; ii<= 30000;ii++){
//
//                 // nx2 BUbble sort...
//
//             }
//
//             Log.d("tag"," in i "+i);
//         }


        myView = new MyView();
        myView.start();

        myWifi = new MyWifi();
        myWifi.start();

//        Thread  thread = new Thread();
//        thread.start();

        new Thread(   new Task() ).start();

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        for(int ii = 0 ; ii<= 50 ;ii++){


                            Log.d("tag"," in diffrent  Run  "+ii);

                        }
                    }
                }


        ).start();


        /////////// UI thread......

       // Looper.prepare();  // will create the msg queue..POST BOX

       handler =  new Handler(){

           @Override
           public void handleMessage(@NonNull Message msg) {
               super.handleMessage(msg);

              // msg.what  ==    ID ( Google )  POBOX ID 7788
              // msg.arg1  = int
               // msg.arg2  = int
              // msg.obj = will hold a string or an object

               switch (msg.what){

                   case 101 :    // deal with the progress bar

                       progressBar1.setProgress(msg.arg1);
                       textView.setText("%"+msg.arg2);

                       if( msg.arg1==9) {
                           // progressBar1.setVisibility(View.GONE);
                           Message message = progressBarHandler.obtainMessage();

                           message.what =502; // TERMINATED
                           message.arg1 = msg.arg1;
                           message.obj =(String)"STATUS : FREE ( Progress Bar terminated )..  Status : ";
                           progressBarHandler.sendMessage(message);
                           // Client Harman Samsung
                           break;

                       }
                       // get a free slot of the worker thread message queue
                       Message message = progressBarHandler.obtainMessage();

                       message.what =501; // Progressing BUSY STATE
                       message.arg1 = msg.arg1;
                       message.obj =(String)"STATUS : BUSY From Progress Bar  : ";

                       progressBarHandler.sendMessage(message);


                       break;


                   case 201 :   // google maps

                       // lets display google map
                       Bundle bundle = new Bundle();
                       bundle = msg.getData();


                      // msg.getData();
                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+Float.parseFloat((String) bundle.get("x"))+
                               ","+Float.parseFloat((String) bundle.get("y") )));
                       startActivity(intent);

                       break;



                   case 301 :// display an image
                   break;

                   case 401 : // paint a circle
                       break;
               }



           }


       };
     //  Looper.loop();   // constantly checks the msg queue for the latest msg and hands over the msg to
        // handleMessage



        /////////////// UI thread ends here..

    }

    public void StopThread(View view) {

    }

    public void StartThread(View view) {
        // worker thread
       // Ui_in_WorkerThread_crash();
        // Solution ?
        // handler is a pointer to the UI handler...( Business logic will not be under UI )

        handler.post(

                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "This toast is comming up from the UI handler..", Toast.LENGTH_SHORT).show();
                    }
        });

    }

    private void Ui_in_WorkerThread_crash() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Toast should be present under UI handler..
                        Toast.makeText(MainActivity.this, "This may not see the light again..", Toast.LENGTH_SHORT).show();
                    }
                }

        ).start();
    }

    public void startProgressBar(View view) {

        // worker thread

        new Thread( new ProgressTask() ).start();



    }

    public class ProgressTask implements  Runnable {

        @Override
        public void run() {

                 for( int i = 0 ; i< 10 ; i++ ){
                     Message message = handler.obtainMessage(); // get a free slot from MSG packet

                     // Lets prepare the msg packet

                     message.what = 101 ;     // My ID ( Progress Bar Worker thread )
                     message.arg1 = i ;
                     message.arg2 = i * 10 ;

                     try {
                         Thread.sleep(500);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }

                     handler.sendMessage(message);


                 }
                 // we willl receive the message from the handler( UI handler )

            Looper.prepare();

                 progressBarHandler = new Handler(){
                     @Override
                     public void handleMessage(@NonNull Message msg) {
                         super.handleMessage(msg);

                         switch (msg.what){

                             case 500:
                                 Log.d("tag","STATUS : Started Received from parent : "+msg.obj);
                                 break;

                             case 501:
                                 Log.d("tag","STATUS : Progress Received from Progress BAR : \n"+msg.obj+" :  "+msg.arg1);
                                 break;
                             case 502:
                                 Log.d("tag","STATUS : TERMINATED Received from Progress BAR : \n"+msg.obj+" :  "+msg.arg1);
                                 break;
                         }


                     }
                 };
                 Looper.loop();

        }// close of Run
    }

    private class ShowMaps implements Runnable{

        @Override
        public void run() {

            // x,y  12.345345345  arg1  int arg2 int


            // 1. Get the message  packet address from the parent
            Message message = handler.obtainMessage();

            // 2 prepare the message packet

            Bundle bundle = new Bundle();
            bundle.putString("x","12.9810782");
            bundle.putString("y"," 77.6921623");
            message.what = 201 ;                // giving my ID
            message.setData(bundle);



            // converting float to String
//            float fNumber = (float) 12.9810782;
//            String fNumberAsString = Float.toString(fNumber);

            // Lets post the message packet to the parent

            handler.sendMessage(message);



        }
    }

    public void showMap(View view) {

        new Thread(new ShowMaps() ).start();
    }

    public  class  Task implements Runnable{


        @Override
        public void run() {
            for(int ii = 0 ; ii<= 50 ;ii++){


                Log.d("tag"," in Task Run  "+ii);

            }


        }
    }
}
