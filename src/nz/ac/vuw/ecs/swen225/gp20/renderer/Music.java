package nz.ac.vuw.ecs.swen225.gp20.renderer;

import java.applet.AudioClip; 
import java.io.*; 
import java.applet.Applet; 
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


/**
 * A class generate sound effect.
 * @author Phoenix Xie
 *
 */
public class Music{
	    
	private String name = "bgm.wav";
	private File f = new File("music/"+name); 
	private URL url; 
	private URI uri;
	private  AudioClip clip; 
	
	/**
	 * Set music name.
	 * @param name music name.
	 */
	void setMusic(String name){
       this.name=name;
   }
	
	/**
	 * Loading music file.
	 */
	public Music(){     
    try {  
       uri=f.toURI();
       url = uri.toURL();
       clip = Applet.newAudioClip(url); 
       clip.loop();
       System.out.println("music on");
   }
    catch (MalformedURLException e) { 
           e.printStackTrace(); 
           System.out.println("error");
       }
   }
  /**
 * Stopping music playing.
 */
	public void stopMusic(){
      clip.stop();
  }
  
/**
 * Start play music.
 */
	public void playMusic(){
      clip.play();
  }


  /**
 *Looping current music file. 
 */
public void loopMusic()
  {
      clip.loop();
  }
	 


	
}
