import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassVersion {
    static Scanner input = new Scanner(System.in);
    static int pump1=0;         //global pump variables to store stock income for each pump queue
    static int pump2=0;
    static int pump3=0;
    static int pump4=0;
    static int pump5=0;
    static int stock = 6600;
    static int front=-1;            //front and rear pointers for circular waiting queue
    static int rear=-1;

    static File file = new File("classV.txt");
    static passenger temp;

    static passenger[] waitingQueue = new passenger[6];         //waitingQueue array
    static FuelQueue[] fuelqueue = new FuelQueue[5];            //fuelQueue objects declaration

    public static void main(String[] args) throws Exception {



        boolean ended=false;

        Initialize();                               //initialize method called

        while(true){


            if (ended){                //ended variable, used to end the whole program if ended=true
                break;
            }
            System.out.println("\n-------------------------------------------------------");
            System.out.println("\nWelcome! Enter your required instruction from below\n");
            System.out.println(
                    """
                            100 or VFQ: View all Fuel Queues.
                            101 or VEQ: View all Empty Queues.
                            102 or ACQ: Add customer to a Queue.
                            103 or RCQ: Remove a customer from a Queue. (From a specific location)
                            104 or PCQ: Remove a served customer.
                            105 or VCS: View Customers Sorted in alphabetical order
                            106 or SPD: Store Program Data into file.
                            107 or LPD: Load Program Data from file.
                            108 or STK: View Remaining Fuel Stock.
                            109 or AFS: Add Fuel Stock.
                            110 or IFQ: View Income of Each Fuel Queue.
                            111 or VWQ: View Waiting Queues
                            112 or GUI: View GUI
                            
                            
                            
                            999 or EXT: Exit the Program.
                            """);

            if (stock == 500) {                 //stock reminder at 500
                System.out.println("Warning! Fuel limit hit 500 liters. Update ASAP");
            }

            if (stock<=0){              //optional feature added, to prompt the user to stop the program if stock reaches 0
                try {
                    System.out.println("""
                            Stock finished!!! Quit program?
                            1- Yes
                            2- No""");
                    int quit=input.nextInt();
                    if (quit==1){
                        break;
                    }
                }

                catch(Exception e)
                {
                    System.out.println("Error!");
                }

            }


            System.out.print("Enter - ");
            String response = input.next();


            switch (response) {

                case "100":
                case "VFQ":
                    for(int i=0;i<=4;i++){
                        System.out.println("\n----FuelQueue "+(i+1)+"----");
                        fuelqueue[i].viewQueue();
                    }

                    break;

                case "101":
                case "VEQ":
                    empty();

                    break;

                case "102":
                case "ACQ":
                    add();
                    break;


                case "103":
                case "RCQ":
                    remove();
                    break;

                case "104":
                    removeServed();
                case "PCQ":
                    break;

                case "105":
                case "VCS":
                    try {
                        System.out.println("Enter Pump Number");
                        int pump = input.nextInt();
                        fuelqueue[pump - 1].sort();

                        break;
                    }
                    catch(Exception e){
                        System.out.println("Error!");
                    }

                case "106":
                case "SPD":
                    for(int i=0;i<5;i++)
                    {
                        store(fuelqueue[i]);
                    }
                    System.out.println("Successful!");

                    break;

                case "107":
                case "LPD":
                    System.out.println("\n----FuelQueue 1----");
                    store(fuelqueue[0]);
                    load(fuelqueue[0]);
                    System.out.println("\n----FuelQueue 2----");
                    store(fuelqueue[1]);
                    load(fuelqueue[1]);
                    System.out.println("\n----FuelQueue 3----");
                    store(fuelqueue[2]);
                    load(fuelqueue[2]);
                    System.out.println("\n----FuelQueue 4----");
                    store(fuelqueue[3]);
                    load(fuelqueue[3]);
                    System.out.println("\n----FuelQueue 5----");
                    store(fuelqueue[4]);
                    load(fuelqueue[4]);

                    break;

                case "108":
                case "STK":
                    System.out.println("\n---View Remaining Fuel Stock.---\n");
                    System.out.print("Stock Remaining - " + stock);             //stock
                    break;

                case "109":
                case "AFS":
                    try {


                        System.out.println("\n---Add Fuel Stock.---\n");
                        System.out.print("Amount added - ");
                        int amount = input.nextInt();
                        stock = stock + amount;

                        System.out.println("\nAdded " + amount + " to Center stock!");
                        System.out.println("Current stock - " + stock);
                        break;

                    } catch (Exception e) {
                        System.out.println("\nInvalid Input");
                        break;

                    }

                case "110":
                case "IFQ":
                    System.out.println("\n----Income of Each FuelQueue----\n");         //stock income
                    System.out.println("FuelQueue1 - $"+pump1);
                    System.out.println("FuelQueue2 - $"+pump2);
                    System.out.println("FuelQueue3 - $"+pump3);
                    System.out.println("FuelQueue4 - $"+pump4);
                    System.out.println("FuelQueue5 - $"+pump5);


                    break;


                case "999":
                case "EXT":

                    System.out.print("Exited!");
                    ended = true;               //end program
                    break;

                case "111":
                case "VWQ":
                    System.out.println("\n----Waiting List----");
                    printWaiting();
                    break;

                case "112":
                case "GUI":
                    gui();
                    break;

                case "remove":
                    waitingRemove();
                    break;

                default:
                    System.out.println("\nInvalid Input!");
                    break;
            }
        }
    }

    public static void remove()                 //remove method
    {
        try {
            System.out.println("Enter Pump Number: ");
            int pump = input.nextInt();
            System.out.println("----FuelQueue" + pump + "----\n");
            fuelqueue[pump - 1].viewQueue();
            System.out.print("\nWhat number to be removed? - ");
            int num = input.nextInt();

            fuelqueue[pump - 1].RemoveCustomer(num - 1);

            System.out.println("\nCustomer " + num + " removed!");
        }
        catch(Exception e)
        {
            System.out.println("\nInvalid!");
        }

    }
    public static void removeServed()
    {
        System.out.println("Enter Pump Number: ");
        int pump = input.nextInt();
        if(fuelqueue[pump-1].sizeQueue()==0)
        {
            System.out.println("Queue already Empty!");
            return;
        }

        if (fuelqueue[pump-1].viewFuel(0)==0)               //checks if liters entered is 0, since 430*0 would reset the-
        {                                                         //-stocks income to 0
            System.out.println("Liters required is none, stocks not updated.");
        }

        else if (pump==1){

            pump1=pump1+(fuelqueue[pump-1].viewFuel(0)*430);            //adding income and decrementing stock to entered queue number
            stock=stock-(fuelqueue[pump-1].viewFuel(0));
        }

        else if (pump==2){
            pump2=pump2+(fuelqueue[pump-1].viewFuel(0)*430);
            stock=stock-(fuelqueue[pump-1].viewFuel(0));
        }

        else if (pump==3){
            pump3=pump3+(fuelqueue[pump-1].viewFuel(0)*430);
            stock=stock-(fuelqueue[pump-1].viewFuel(0));
        }

        else if (pump==4){
            pump4=pump4+(fuelqueue[pump-1].viewFuel(0)*430);
            stock=stock-(fuelqueue[pump-1].viewFuel(0));
        }

        else if (pump==5){
            pump5=pump5+(fuelqueue[pump-1].viewFuel(0)*430);
            stock=stock-(fuelqueue[pump-1].viewFuel(0));
        }

        try {
            fuelqueue[pump - 1].RemoveCustomer(0);
            System.out.println("\nCustomer Served!");

            if (!waitingEmpty()){              //adds to waiting queue if waiting queue contains elements
                fuelqueue[pump-1].AddCustomer(waitingQueue[front]);
                System.out.println(waitingQueue[front].getFirstName()+" added from Waiting Queue\n");
                waitingRemove();
            }
        }
        catch(Exception e){
            System.out.println("\nInvalid!");
        }

    }

    public static void empty()              //empty method
    {
        for(int i=0;i<5;i++)
        {
            if (fuelqueue[i].viewEmptyQ())
            {
                System.out.println("\n----FuelQueue "+(i+1)+"----");
                System.out.println("\nEmpty");
            }
        }
    }
    public static void add()
    {
        try {
            System.out.print("Enter Passenger FirstName: ");
            String fName = input.next();
            System.out.print("Enter Passenger LastName: ");
            String lName = input.next();
            System.out.print("Enter Vehicle Number: ");
            String vNo = input.next();
            System.out.print("Enter Liters: ");
            int liters = input.nextInt();

                //Temporary ArrayList created to store the size of each fuelqueue

            ArrayList<Integer> qSizeArr = new ArrayList<Integer>(5);

            for (int i = 0; i < 5; i++) {
                qSizeArr.add(fuelqueue[i].sizeQueue());
            }

            System.out.println(qSizeArr);

            int min = (int) qSizeArr.toArray()[0];              //code used to find the index of the smallest size element
            int index = 0;

            for (int i = 0; i < qSizeArr.size(); i++) {
                if (min > (int) qSizeArr.toArray()[i]) {
                    min = (int) qSizeArr.toArray()[i];
                    index = i;
                }
            }

            if (fuelqueue[index].sizeQueue() == 6) {            //if size is full, to print all queues full
                System.out.println("\nAll Queues full");
                waitingAdd(fName,lName,vNo,liters);

            } else {


                fuelqueue[index].AddCustomer(new passenger(fName, lName, vNo, liters));         //add customer
                System.out.println("\nAdded " + fuelqueue[index].viewName(fuelqueue[index].sizeQueue() - 1) + " to FuelQueue" + (index + 1));
            }
        }
        catch(Exception e){
            System.out.println("\nInvalid!");
        }
    }
    public static void Initialize()         //Initialize method, to initialize each fuelqueue object
    {
        for(int i = 0; i< fuelqueue.length; i++)
        {
            fuelqueue[i]=new FuelQueue(i+1);

        }
    }

    public static void store(FuelQueue fuelQueue) throws Exception
    {

        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(fuelQueue);




    }

    public static void load(FuelQueue fuelQueue) throws Exception
    {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        FuelQueue loadFqueue = (FuelQueue) ois.readObject();
        for(int i=0;i<6;i++) {
            try {
                System.out.println("\n----Customer " + (i + 1) + "----");
                System.out.println("\nFirst Name = " + loadFqueue.viewName(i));
                System.out.println("\nSecond Name = " + loadFqueue.viewSecondName(i));
                System.out.println("\nVehicle Number = " + loadFqueue.viewVehicleNo(i));
                System.out.println("\nNumber of Liters = " + loadFqueue.viewFuel(i));
            }
            catch(Exception e)
            {
                break;
            }

        }
    }
    public static void waitingAdd(String fName, String lName, String vNo, int liters) //waitingAdd method
    {
        if(waitingFull()){              //waitingFull method used to check if circular waiting list is full
            System.out.println("Waiting Queue is full");
        }
        else {
            if (front == -1)
            {
                front = 0;
            }
            rear=(rear+1)%waitingQueue.length;          //circular queue formula
            waitingQueue[rear]=new passenger(fName,lName,vNo,liters);
            System.out.println("Added into waiting Queue!");
        }
    }

    public static void waitingRemove()              //waitingRemove method
    {

        if(waitingEmpty()){
            System.out.println("\nEmpty!\n");

        }
        else {

            temp = waitingQueue[front];

            if (front == rear) {
                front = -1;
                rear = -1;
            } else if (front == waitingQueue.length - 1) {
                front = 0;
            } else {
                front = front + 1;
            }
        }

    }

    public static void printWaiting()       //not part of question, created this to print the waiting list separately
    {
        if(waitingEmpty())
        {
            System.out.print("\nQueue is Empty\n");
            return;
        }
        if(rear>=front) {
            for (int i = front; i <= rear; i++) {
                System.out.println("\n~~~Customer " + (i + 1) + "~~~");
                System.out.println("\nFirst Name = " + waitingQueue[i].getFirstName());
                System.out.println("Second Name = " + waitingQueue[i].getSecondName());
                System.out.println("Vehicle Number = " + waitingQueue[i].getVehicleNo());
                System.out.println("Number of Liters = " + waitingQueue[i].getLiters());
            }
            System.out.println();
        }

        else{
            for(int i=front;i<waitingQueue.length;i++)
            {
                System.out.println("\n~~~Customer " + (i + 1) + "~~~");
                System.out.println("\nFirst Name = " + waitingQueue[i].getFirstName());
                System.out.println("Second Name = " + waitingQueue[i].getSecondName());
                System.out.println("Vehicle Number = " + waitingQueue[i].getVehicleNo());
                System.out.println("Number of Liters = " + waitingQueue[i].getLiters());
            }

            for(int i=0;i<=rear;i++)
            {
                System.out.println("\n~~~Customer " + (i + 1) + "~~~");
                System.out.println("\nFirst Name = " + waitingQueue[i].getFirstName());
                System.out.println("Second Name = " + waitingQueue[i].getSecondName());
                System.out.println("Vehicle Number = " + waitingQueue[i].getVehicleNo());
                System.out.println("Number of Liters = " + waitingQueue[i].getLiters());
            }
            System.out.println();

        }

    }
    public static boolean waitingFull()         //waitingFull method to check if circular waiting queue is full
    {
        if(front==0 && rear==waitingQueue.length-1){
            return true;
        }

        if (front==rear+1){
            return true;

        }

        return false;

    }

    public static void gui() {                  //gui method


        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation((JFrame.HIDE_ON_CLOSE));
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);
        StringBuilder text = new StringBuilder();




    for (int i = 0; i < 5; i++) {
        try {
            text.append("\n\nQueue" + (i + 1));
        }
        catch(Exception e){
            continue;
        }

        for (int j = 0; j < 6; j++) {
            try {
                text.append("\nCustomer " + (j + 1)+"   |   ");
                text.append("First Name: " + fuelqueue[i].viewName(j)+"   |   ");
                text.append("Second Name: " + fuelqueue[i].viewSecondName(j)+"   |   ");
                text.append("Vehicle No: " + fuelqueue[i].viewVehicleNo(j)+"   |   ");
                text.append("Liters Required: " + fuelqueue[i].viewFuel(j)+"   |   ");

                if (i % 5 == 0 && i > 0) text.append("\n");
            }
            catch(Exception e){
                continue;
            }
            finally{

            }

        }


    }


text.delete(text.length()-2, text.length()-1);
JOptionPane.showMessageDialog(null,"Queues : "+text.toString());

        frame.setVisible(true);


    }

    public static boolean waitingEmpty()            //waitingEmpty method to check if circular waiting queue is empty
    {
        if (front==-1) {
            return true;
        }
        else{
            return false;
        }
    }




}

