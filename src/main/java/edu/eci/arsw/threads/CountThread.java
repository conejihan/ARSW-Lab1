/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import java.lang.*;


/**
 *
 * @author hcadavid
 */
public class CountThread implements Runnable{
	int a;
	int b;
	
	public CountThread(int i, int j) {
		a = i;
		b = j;
	}
	
	@Override
	public void run() {
		for(int i = a; i < b; i++) {
			System.out.println(i);
		}
	}
    
}
