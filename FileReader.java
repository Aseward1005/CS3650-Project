import java.io.*;
import java.util.*;
public class FileReader {
    private Schedule schedule;

    public FileReader(Schedule schedule){
        this.schedule = schedule;
    }

    public void read(String filename){
        Scanner in = new Scanner(new File(filename));
        String next1 = "";
        String next2 = "";
        ArrayList<String> taskData = new ArrayList<String>();
        ArrayList<Task> newTasks = new ArrayList<Task>();
        try{
            in.next();
            while(in.hasNext()){
                next1 = in.next();
                taskData.add(next1.substring(1, next1.length()-2));
                next1 = in.next();
                next2 = in.next();
                if(next1.equals("[") && next2.equals("{")){
                    while(!(next1.equals("},"))){
                        next1 = in.next();
                        next2 = in.next();
                        if(next2.subString(next2.length()-1).equals(",")){
                            next2 = next2.substring(1, next2.length()-2);
                        }
                        else if(next2.subString(next2.length()-1).equals("\"")){
                            next2 = next2.substring(1, next2.length()-1);
                        }
                        else{
                            throw new Exception("Invalid format");
                        }
                        taskData.add(next2);
                    }
                }
                else{
                    throw new Exception("Invalid format");
                }
                newTasks.add(new Task(taskData.get(0), taskData.get(1), taskData.get(2)));
                in.next();
                in.next();
            }
        }
        catch (Exception e){
            throw new Exception("Invalid format");
        }
        for(Task task : newTasks){
            try{
                if (schedule.checkConflicts(task))
                    schedule.addTask(task);
                else{
                    System.out.println("Task not added because of conflict");
                    task.show();
                }
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(iae.getMessage());
                task.show();
            }

        }
    }
}
