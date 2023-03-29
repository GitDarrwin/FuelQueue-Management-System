import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FuelQueue implements Serializable {

    ArrayList<passenger> queue = new ArrayList<passenger>(6);           //Arraylist queue created for each fuelqueue object

    int QueueNumber;

    public FuelQueue(int QueueNumber)
    {
        this.QueueNumber=QueueNumber;
    }

    public void AddCustomer(passenger p1)
    {
        queue.add(p1);
    }

    public void RemoveCustomer(int index)
    {
        queue.remove(index);
    }


    public boolean viewEmptyQ()             //viewEmptyQ checks if queue is empty and returns true;
    {
        if (queue.isEmpty()==true){
            return true;
        }

        else{
            return false;
        }
    }
    public void viewQueue() {
        if (queue.isEmpty() == true) {
            System.out.println("\nEmpty");
        } else {
            for (int i = 0; i < queue.size(); i++) {
                System.out.println("\n~~~Customer " + (i + 1) + "~~~");
                System.out.println("\nFirst Name = " + queue.get(i).getFirstName());            //calls passenger class getter for each attribute
                System.out.println("Second Name = " + queue.get(i).getSecondName());
                System.out.println("Vehicle Number = " + queue.get(i).getVehicleNo());
                System.out.println("Number of Liters = " + queue.get(i).getLiters());


            }
        }
    }

    public String viewSecondName(int index) {
        return queue.get(index).getSecondName();
    }


    public String viewVehicleNo(int index) {
        return queue.get(index).getVehicleNo();
    }

    public String viewName(int index) {
        return queue.get(index).getFirstName();
    } //viewName calls only the First name

    public int sizeQueue()
    {
        return queue.size();
    }

    public boolean checkFull()
    {
        return queue.isEmpty();
    }

    public int viewFuel(int index)
    {
        return queue.get(index).getLiters();
    }

    public void sort(){
        if (queue.isEmpty()){
            System.out.println("Queue Empty!");
        }
        else
        {
            ArrayList<passenger> sorted = (ArrayList<passenger>)queue.clone();          //temporary arraylist created to add sorted elements into it.
            System.out.println("Sorted - ");
            sorted.sort(Comparator.comparing(passenger::getFirstName));         //Comparator sort function used to sort with only firstNames

            for (int i = 0; i < sorted.size(); i++) {
                System.out.println("\n~~~Customer " + (i + 1) + "~~~");             //prints sorted array
                System.out.println("\nFirst Name = " + sorted.get(i).getFirstName());
                System.out.println("Second Name = " + sorted.get(i).getSecondName());
                System.out.println("Vehicle Number = " + sorted.get(i).getVehicleNo());
                System.out.println("Number of Liters = " + sorted.get(i).getLiters());
        }
        }


        }
    }


