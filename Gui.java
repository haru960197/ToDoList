import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame implements ActionListener{
    ToDoList toDoList;
    JButton buttonArray[];
    JLabel labelArray[];
	private Container c;
    MakeDoneListener aMakeDoneListener;
    RemoveListener aRemoveListener;
    JButton addButton;

    public Gui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ToDoリスト管理");
        setSize(510, 370);
        c = getContentPane();
        c.setLayout(null);

        this.toDoList = new ToDoList();
        aMakeDoneListener = new MakeDoneListener(this);
        aRemoveListener = new RemoveListener(this);

        buttonArray = new JButton[10];
        labelArray = new JLabel[10];
        for (int i=0; i<10; i++) {
            buttonArray[i] = new JButton("〇");
            c.add(buttonArray[i]);
            buttonArray[i].setBounds(10,i*30 + 10,20,20);
            buttonArray[i].setMargin(new Insets(0, 0, 0, 0));
            buttonArray[i].setEnabled(false);
			buttonArray[i].setActionCommand(Integer.toString(i));

            labelArray[i] = new JLabel();
            c.add(labelArray[i]);
            labelArray[i].setBounds(35,i*30 + 10,300,20);
        }
        
        addButton = new JButton("タスクを追加");
        c.add(addButton);
        addButton.setBounds(350,120,130,40);
        addButton.setMargin(new Insets(5, 5, 5, 5));
        addButton.addActionListener(this);
        
    }

    public void actionPerformed(ActionEvent e) {
        String taskContent = JOptionPane.showInputDialog
                (this, "タスクの内容を入力してください");
        if (taskContent == null) {
            System.out.println("NULL");
        } else {
            this.toDoList.add(taskContent);
        }
        showToDoList();
        renewListener();
    }

    public void showToDoList() {
        toDoList.sort();
        int size = toDoList.size();
        for (int i=0; i<size; i++) {
            Task aTask = toDoList.get(i);
            buttonArray[i].setEnabled(true);
            if (!aTask.isDone()) {
                // タスクは未実行　〇
                buttonArray[i].setText("〇");
            } else {
                // タスクは実行済み　●
                buttonArray[i].setText("●");
            }
            labelArray[i].setText(aTask.getContent());
        }
        for (int i=size; i<10; i++) {
            buttonArray[i].setText("〇");
            buttonArray[i].setEnabled(false);
            labelArray[i].setText("");
        }
        // タスク数が10以下になるように調整
        if (toDoList.size() == 10) {
            addButton.setText("タスクが一杯です");
            addButton.setEnabled(false);
        } else {
            addButton.setText("タスクを追加");
            addButton.setEnabled(true);
        }
    }
    public void renewListener() {
        // 全ボタンの全リスナーを削除
        for (int i=0; i<10; i++) {
            for (ActionListener listener : buttonArray[i].getActionListeners()) {
                buttonArray[i].removeActionListener(listener);
            }
        }
        // ToDoリストの中身にしたがって、リスナーを設定
        for (int i=0; i<toDoList.size(); i++) {
            Task aTask = toDoList.get(i);
            if (!aTask.isDone()) {
                // タスクは未実行
                buttonArray[i].addActionListener(aMakeDoneListener);
            } else {
                // タスクは実行済み
                buttonArray[i].addActionListener(aRemoveListener);
            }
        }
    }
}

class MakeDoneListener implements ActionListener {
    // 未実行タスクを実行済みタスクに変更
    Gui gui;
    public MakeDoneListener(Gui gui) {
        this.gui = gui;
    }
    public void actionPerformed(ActionEvent e) {
        int index = Integer.valueOf(e.getActionCommand());
        gui.toDoList.get(index).setState(true);
        gui.showToDoList();
        gui.renewListener();
    }
}
class RemoveListener implements ActionListener {
    Gui gui;
    public RemoveListener(Gui gui) {
        this.gui = gui;
    }
    public void actionPerformed(ActionEvent e) {
        int index = Integer.valueOf(e.getActionCommand());

        int option = JOptionPane.showConfirmDialog
                    (gui, "タスクを削除しますか？(\"いいえ\"で未実行にします)");
        if (option == JOptionPane.YES_OPTION) {
            gui.toDoList.remove(index);
        } else if (option == JOptionPane.NO_OPTION) {
            gui.toDoList.get(index).setState(false);
        }
        gui.showToDoList();
        gui.renewListener();
    }
}
