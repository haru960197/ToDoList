import java.util.ArrayList;

public class ToDoList {
    private ArrayList<Task> taskList = new ArrayList<Task>();
    
    public Task get(int index) {
        return this.taskList.get(index);
    }
    public void add(String content) {
        this.taskList.add(new Task(content));
        this.sort();
    }
    public void remove(int index) {
        this.taskList.remove(index);
    }
    public void makeDone(int index, boolean state) {
        this.taskList.get(index).setState(state);
        this.sort();
    }
    public void clear() {
        this.taskList.clear();
    }
    public int size() {
        return this.taskList.size();
    }
    public void sort() {
        // リストは先頭から未実行のタスクが、その後ろに実行済みのタスク
        ArrayList<Task> newTaskList = new ArrayList<Task>();
        for (int i=0; i<this.taskList.size(); i++) {
            Task aTask = taskList.get(i);
            if (!aTask.isDone()) {
                // 未実行
                newTaskList.add(aTask);
            }
        }
        for (int i=0; i<this.taskList.size(); i++) {
            Task aTask = taskList.get(i);
            if (aTask.isDone()) {
                // 実行済
                newTaskList.add(aTask);
            }
        }
        this.taskList = newTaskList;
    }
    
    public void display() {
        for (int i=0; i<this.taskList.size(); i++) {
            Task aTask = taskList.get(i);
            System.out.println("index:" + i + " Content:" + aTask.getContent()
                    + " isDone:" + aTask.isDone());
        }
        System.out.println("=================================");
    }
    /*
    public static void main(String[] args) {
        ToDoList list = new ToDoList();
        list.add("hoge");
        list.add("fuga");
        list.add("piyo");
        list.display();        
        list.makeDone(1, true);
        list.display();
        list.remove(1);
        list.display();
    }
    */
}