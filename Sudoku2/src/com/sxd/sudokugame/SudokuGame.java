package com.sxd.sudokugame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SudokuGame {
	public static void main(String[] args){
		System.out.println("Please input the level you want to play\n(23~30 are recommended)\nInput '0' to quit");
		Scanner in = new Scanner(System.in);
		while(true){
			int n = in.nextInt();
			if(n==0){
				System.out.println("Thank you for using");
				break;
			}
			if(n<0||n>81){
				System.out.println("Illegal input");
			}
			Sudoku sudoku = new Sudoku();
			sudoku.getRandomMap(81-n);
			System.out.println("The answer is");
			sudoku.getAnswer();
		}
	}
}

class Sudoku{
	int[][] map = new int[9][9];
	boolean solved = false;
	
	Sudoku(){
		
	}
	
	Sudoku(int[][] imap){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				map[i][j] = imap[i][j];
			}
		}
	}
	
	void getRandomMap(int n){	
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
		Collections.shuffle(list);
		Integer[] sign = new Integer[9];
		list.toArray(sign);
		for(int i=0;i<9;i++){
			map[(int) (i/3*3+(int)(Math.random()*9/3))][(int) (i%3*3+(int)(Math.random()*9)%3)] = (int)sign[i];
		}
		run();
		for(int i=0;i<n;){
			double temp = Math.random();
			if(map[i%9][(int)(temp*9)]!=0){
				map[i%9][(int)(temp*9)]=0;
				i++;
			}
		}
		this.display();
		solved = false;
	}
	
	void getAnswer(){
		run();
		display();
	}
	
	void display(){
		System.out.println("----------------------");
		for(int i = 0;i<9;i++){
			System.out.print("|");
			for(int j = 0;j<9;j++){
				System.out.print("" + map[i][j] + ' ');			
				if(j%3==2){
					System.out.print("|");
				}
			}
			if(i%3==2){
				System.out.print("\n----------------------");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	int possible(int x,int y,int[] exist){
		int result=0;
		for(int i=1;i<10;i++){
			exist[i]=0;
		}
		for(int i=0;i<9;i++){
			exist[map[x][i]]=1;
			exist[map[i][y]]=1;
			exist[map[x/3*3+i/3][y/3*3+i%3]]=1;
		}
		for(int i=1;i<10;i++){
			if(exist[i]==0){
				result++;
			}
		}
		return result;		
	}
	
	void run(){
		if(solved){
			return;
		}
		int[] exist = new int[10];
		int a=-1,b=-1;
		boolean over = true;
		int minpossible = 10;
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(map[i][j]!=0){
					continue;
				}
				int p = possible(i,j,exist);
				if(p==0){
					return;
				}
				if(p<minpossible){
					a=i;
					b=j;
					minpossible=p;
					over = false;
				}
			}
		}
		if(over){
			solved = true;
			return;
		}
		possible(a,b,exist);
		for(int i=1;i<10;i++){
			if(exist[i]==0){
				map[a][b]=i;
				run();
				if(solved){
					return;
				}
			}
		}
		map[a][b]=0;
	}
	
}