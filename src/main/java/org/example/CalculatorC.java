package org.example;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Alienware
 * @Description
 * @create 2023/7/7 - 17:55
 */
public class CalculatorC extends JFrame {
    JTextField calcTextField;
    Button[] buttons = new Button[20];
    String[] buttonText = {"9","8","7","X","4","5",
            "6","/","1","2","3","-","0","(",")","+"," "," ","C","="};
    int ans=0, nk=0;
    String opr=" ", key=" ";
    Stack<Integer> stackN = new Stack<Integer>();
    Stack<String> stackO = new Stack<String>();

    public static String[] low = {"+","-"};
    public static String[] high = {"X","/"};

    public static Font TextFont = new Font("SansSerif", Font.BOLD, 20);
    public static Font BtnFont = new Font("SansSerif", Font.BOLD, 14);

    public CalculatorC() {
        setSize(200, 250);
        calcTextField = new JTextField(16);
        calcTextField.setFont(TextFont);
        calcTextField.setEditable(false);
        calcTextField.setBackground(Color.white);
        calcTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        calcTextField.setText("0");
        Border border = BorderFactory.createLineBorder(Color.gray, 5);
        calcTextField.setBorder(border);

        JPanel btnPanel = new JPanel(new GridLayout(5, 4));
        for (int i=0; i<20; i++) {
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
        System.out.println("nk1: " + ans + " opr: " + opr + " nk2: " +nk);
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

        if (key.equals("C")) {
            nk = 0;
            opr = " ";
            ans = 0;
            key = " ";
            stackN = new Stack<Integer>();
            stackO = new Stack<String>();
            calcTextField.setText("0");
        }
        else if(isInteger(key) && (Integer.parseInt(key) >= 0) && (Integer.parseInt(key) <= 9)) {
            if(opr.equals("=")) {
                nk = 0;
                ans = 0;
                stackN = new Stack<Integer>();
                stackO = new Stack<String>();
                opr = " ";
            }
            nk = 10 * nk + Integer.parseInt(key);
            System.out.println("nk: " + nk);
            calcTextField.setText("" + nk);
        }
        else if(key.equals("(")) {
            if(opr.equals("=")) {
                nk = 0;
                ans = 0;
                stackN = new Stack<Integer>();
                stackO = new Stack<String>();
                opr = " ";
            }
            stackO.push(key);
            if (stackN.size() >= 1)
                calcTextField.setText("" + stackN.peek());
            else {
                calcTextField.setText("0");
            }
        }
        else if(key.equals(")")) {
            if(nk != 0){
                stackN.push(nk);
                nk = 0;
            }
            int n2 = stackN.pop();
            int n1 = stackN.pop();
            opr = stackO.pop();
            if(stackO.peek().equals("(")) {
                stackO.pop();
            }
            ans = apply(n1,opr,n2);
            calcTextField.setText("" + ans);
            stackN.push(ans);
            System.out.println("ans: " + ans);
        }
        else if(key.equals("=")) {
            if(nk != 0){
                stackN.push(nk);
                nk = 0;
            }
            while(stackN.size() > 1) {
                if(stackO.peek().equals("(")){
                    stackO.pop();
                }
                int n2 = stackN.pop();
                int n1 = stackN.pop();
                String opr = stackO.pop();
                ans = apply(n1, opr, n2);
                stackN.push(ans);
                System.out.println("ans: " + ans);
            }
            calcTextField.setText("" + stackN.peek());
            opr = "=";
        }
        // + - X /
        else {
            if(nk != 0){
                stackN.push(nk);
            }
            // First opr in the stack
            if(stackO.size() == 0) {
                stackO.push(key);
                if(ans != 0) {
                    calcTextField.setText("" + ans);
                } else {
                    calcTextField.setText("" + nk);
                }
                nk = 0;
            }
            // Current opr is High precedence
            else if(Arrays.asList(high).contains(key) && Arrays.asList(low).contains(stackO.peek())) {
                stackO.push(key);
                if(ans != 0) {
                    calcTextField.setText("" + ans);
                } else {
                    calcTextField.setText("" + nk);
                }
                nk = 0;
            }
            // Current opr is low precedence
            else if(Arrays.asList(low).contains(key) && Arrays.asList(high).contains(stackO.peek())) {
                String high = stackO.pop();
                int n2 = stackN.pop();
                int n1 = stackN.pop();
                ans = apply(n1, high, n2);
                stackN.push(ans);
                stackO.push(key);
                calcTextField.setText("" + nk);
                nk = 0;
                System.out.println("ans: " + nk);
            } else {
                stackO.push(key);
                nk = 0;
            }
            opr = key;
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
        new CalculatorC().setVisible(true);
    }
}
