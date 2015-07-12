package com.example;

public class EnglishSpeaker implements Speaker {

	public void speak() {
		System.out.println("Hello world");
	}
}

interface Speaker {
	void speak();
}