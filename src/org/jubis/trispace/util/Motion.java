package org.jubis.trispace.util;


public class Motion {
	public Speed speed;
	public float anguralSpeed;
	
	public Motion( Speed speed, float angularSpeed ) {
		this.speed = speed;
		this.anguralSpeed = angularSpeed;
	}
	
	public String toString() {
		return this.speed.toString() + "::" + this.anguralSpeed;
	};
}
