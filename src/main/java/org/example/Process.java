
package org.example;

public class Process {
    private int time = 0;
    private int id;
    public Process(int id, int time){
        this.time = time;
        this.id = id;
    }

    public void DecreaseTime(int time){
        this.time -= time;

    }

    public void PrintTime(){
        System.out.println("time for process is = " + this.time);
    }

    public int getId() {
        return id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                " , time=" + time +

                '}';
    }
}
