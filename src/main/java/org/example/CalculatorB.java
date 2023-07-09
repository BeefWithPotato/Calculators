package org.example;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Stack;

/**
 * @author Haosheng Zhang (hzhan686)
 * @Description
 * @create 2023/7/6 - 15:53
 */
public class CalculatorB extends JFrame {
    JTextField calcTextField;
    Button[] buttons = new Button[16];
    String[] buttonText = {"9","8","7","X","4","5",
            "6","/","1","2","3","-","0","C","Enter","+"};
    int ans=0, nk=0;
    String opr=" ", key=" ";
    Stack<Integer> stack = new Stack();

    public static Font TextFont = new Font("SansSerif", Font.BOLD, 20);
    public static Font BtnFont = new Font("SansSerif", Font.BOLD, 14);

    public CalculatorB() {
        setSize(200, 250);
        calcTextField = new JTextField(16);
        calcTextField.setFont(TextFont);
        calcTextField.setEditable(false);
        calcTextField.setBackground(Color.white);
        calcTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        calcTextField.setText("0");
        Border border = BorderFactory.createLineBorder(Color.gray, 5);
        calcTextField.setBorder(border);

        JPanel btnPanel = new JPanel(new GridLayout(4, 4));
        for (int i=0; i<16; i++) {
            buttons[i] = new Button ("" + buttonText[i]);
            buttons[i].setFont(BtnFont);
            btnPanel.add(buttons[i]);
        }

        JPanel calPanel = new JPanel(new BorderLayout());
        calPanel.add(calcTextField, BorderLayout.PAGE_START);
        calPanel.add(btnPanel, BorderLayout.CENTER);
        getContentPane().add(calPanel);
    }

    public int apply (int ans, String opr, int nk)  {
        System.out.println("ans1: " + ans + " opr: " + opr + " nk: " +nk);
        switch (opr) {
            case "+": return ans += nk;
            case "-": return ans -= nk;
            case "X": return ans *= nk;
            case "/": return ans /= nk;
        } return -1;
    }

    public boolean action(Event ce, Object co)  {
        key = (String) co;
        System.out.println("key: " + key);
        System.out.println(key.equals("C"));
        if (key.equals("C")) {
            nk = 0;
            opr = " ";
            ans = 0;
            key = " ";
            stack = new Stack<Integer>();
            calcTextField.setText("0");
        }
        else if(isInteger(key) && (Integer.parseInt(key) >= 0) && (Integer.parseInt(key) <= 9)) {
            if (ans != 0) {
                stack.push(ans);
                ans = 0;
            }
            nk = 10 * nk + Integer.parseInt(key);
            System.out.println("nk: " + nk);
            calcTextField.setText("" + nk);
        }
        else if (key.equals("Enter")) {
            calcTextField.setText(""+nk);
            System.out.println("nk: " + nk);
            stack.push(nk);
            nk = 0;
        }
        else {
            opr = key;
            if(nk != 0){
                stack.push(nk);
                nk = 0;
            }
            if (stack.size() == 1) {
                int n1 = stack.pop();
                ans = apply (n1,opr,n1);
            }
            else {
                int n2 = stack.pop();
                int n1 = stack.pop();
                ans = apply (n1,opr,n2);
                stack.push(ans);
            }
            System.out.println("ans: " + ans);
            calcTextField.setText("" + ans);
            nk = 0;
        }
        return true;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static void main(String[] s){
        new CalculatorB().setVisible(true);
    }
}
