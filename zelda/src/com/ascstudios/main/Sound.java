package com.ascstudios.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	private Clip clip;
	
	public static final Sound musicBackground = new Sound("/music.wav");
	public static final Sound hurtEffect = new Sound("/hurt.wav");
	public static final Sound peiSound = new Sound("/peiSound.wav");
	
	private Sound(String name) {
		try {
			URL soundURL = Sound.class.getResource(name);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch(Throwable e) {}
	}
	
	public void play() {
		if(clip != null) {
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void loop() {
		if(clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
}
