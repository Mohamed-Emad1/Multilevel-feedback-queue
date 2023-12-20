package org.example;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    static ArrayList<Process> processes;

    public static void generateProcess() {

        Random random = new Random();
        processes = new ArrayList<Process>();
        for (int i = 0; i <= 99; i++) {
            int time = random.nextInt(100) + 1; //generate random time for process from 1ms to 100ms
            System.out.println("time for process  = " + time + " id = " + i);
            processes.add(new Process(i + 1, time));

        }

    }

    public static void main(String[] args) {
        generateProcess();

        Queue_pr q1 = new Queue_pr(8, 10);  //first queue with time_quantum and size
        Queue_pr q2 = new Queue_pr(16, 20); //second queue with time_quantum and size
        Queue_pr q3 = new Queue_pr(0, 30);  //third queue with time_quantum and size
        int totalCPUTime =0;
        int q1Time = 0;
        int q2Time = 0;
        int q3Time = 0;

        while (!processes.isEmpty() || q1.getCurrentElements() > 0 || q2.getCurrentElements() > 0 || q3.getCurrentElements() > 0) {

            boolean flag = false; //to check if new processes enqueued in q1
            while (!processes.isEmpty() && q1.getCurrentElements() < q1.getSize()) { //store 10 processes at first time and store
                Process p = processes.remove(0);                                //any process if queue has an empty place
                q1.enqueue(p);
                totalCPUTime += p.getTime();
                flag =true;
            }

            if (flag) {
                q1Time = (int) (0.5 * totalCPUTime);   //50% of CPUTime
                q2Time = (int) (0.2 * totalCPUTime);   //20% of CPUTime
                q3Time = (int) (0.3 * totalCPUTime);   //30% of CPUTime
            }

           //Enter first queue and operate 10 processes or fewer according to time
            for (int i = 0; i < 10 && q1.getCurrentElements() > 0 && q1Time !=0; i++) {
                Process p = q1.dequeue();
                if (p != null) {
                    q1Time -= p.getTime();
                    q1.operate(p);           //operate on every process in q1
                    if (p.getTime() > 0) {   //if process doesn't finish move it to second queue
                        q2.enqueue(p);
                    } else {
                        p.setTime(0);
                        System.out.println("exits " + p.toString() + " from queue 1");
                    }
                }
            }

            //for q2 with more time quantum
            int sizeQueue2 = q2.getCurrentElements();
            for (int i = 0; i < 20 && i < sizeQueue2  && q2Time !=0; i++) {
                Process p = q2.dequeue();
                q2Time -= p.getTime();
                q2.operate(p);           //operate on every process in q2
                if (p.getTime() <= 0) {
                    p.setTime(0);
                    System.out.println("exits " + p.toString() + " from queue 2");
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Random random = new Random();
                    int decisionNumber = random.nextInt(100) + 1;

                    if (decisionNumber < 50 && q1.getCurrentElements() < q1.getSize()) {  //50% move to queue 1 or queue 3
                        boolean check2 = q1.enqueue(p);
                        if (!check2) {  //if you couldn't move to queue 1 move it to queue 3
                            q3.enqueue(p);
                        }
                    }
                }
            }

            // for q3 FCFS
            int queue3Size = q3.getCurrentElements();
            for (int i = 0; i < 30 && i < queue3Size && q3Time !=0; i++) {
                Process p = q3.dequeue();
                q3Time -= p.getTime();
                q3.operate(p);              //operate on every process in q3
                if (p.getTime() <= 0) {
                    p.setTime(0);
                    System.out.println("exits " + p.toString() + " from queue 3");
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}