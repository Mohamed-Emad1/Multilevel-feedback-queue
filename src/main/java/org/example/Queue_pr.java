package org.example;

import java.util.LinkedList;
import java.util.Queue;
import org.example.Process;
public class Queue_pr {
    private Queue<Process> queue ;
    private  int timeForQueue = 0;
    private int count = 0 ;
    private int size = 0;
    public Queue_pr(int time,int size){
        this.queue = new LinkedList<Process>();
        this.timeForQueue = time;
        this.size =size;
    }


    public boolean enqueue(Process c){

        if (this.size > count) {
            this.queue.offer(c);
            this.count ++;
            return true;
        }

    return false;
    }

    public void operate(Process p ){
        if (this.timeForQueue == 0){
            p.DecreaseTime(p.getTime());
        }
        else {
        p.DecreaseTime(this.timeForQueue);
        }
    }

    public  Process dequeue(){
        if (!this.queue.isEmpty()) {
            this.count--;
            return this.queue.poll();

        }
        else {
            return null;
        }
    }

    public void print(){
        for (Process process : this.queue) {
            System.out.println(process.toString());
        }
    }


    public int getSize() {
        return size;
    }

    public int getCurrentElements() {
        return this.count;
    }

    public boolean isEmpty(){
        return  this.queue.isEmpty();
    }
}
