package com.ascstudios.main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	private Clip clip;
	//	Class Game
	public static final Sound musicBackground = new Sound("/music.wav");
	//	Class Enemy
	public static final Sound hurtEffect = new Sound("/hurt.wav");
	//	Class Player
	public static final Sound peiSound = new Sound("/peiSound.wav");
	
	public float volume = 1.0f;
	
	private Sound(String name) {
		try {
			URL soundURL = Sound.class.getResource(name);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		}catch(Throwable e) {}
	}
	
	public void increaseVolume(float step) {
		setVolume(Math.min(1.0f,  volume + step));
	}
	public void decreaseVolume(float step) {
		setVolume(Math.max(0.0f, volume - step));
	}
	
	public void setVolume(float volume) {
		if(volume >= 0.0f && volume <= 1.0f) {
			this.volume = volume;
		}
	}
	
	public void play() {
		if(clip != null) {
			clip.setFramePosition(0);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(20f * (float) Math.log10(volume));
			clip.start();
		}
	}
	
	public void loop() {
		if(clip != null) {
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(20f * (float) Math.log10(volume));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
}