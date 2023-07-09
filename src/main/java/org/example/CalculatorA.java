package org.example;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Stack;

/**
 * @author Haosheng Zhang (hzhan686)
 * @Description
 * @create 2023/7/4 - 18:41
 */
public class CalculatorA extends JFrame {
    JTextField calcTextField;
    Button[] buttons = new Button[16];
    char[] buttonText={'9','8','7','X','4','5',
            '6','/','1','2','3','-','0','C','=','+'};
    int ans=0, nk=0;
    char opr=' ', key=' ';

    public static Font TextFont = new Font("SansSerif", Font.BOLD, 20);
    public static Font BtnFont = new Font("SansSerif", Font.BOLD, 14);

    public CalculatorA() {
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
            buttons[i] = new Button (""+buttonText[i]);
            buttons[i].setFont(BtnFont);
            btnPanel.add(buttons[i]);
        }

        JPanel calPanel = new JPanel(new BorderLayout());
        calPanel.add(calcTextField, BorderLayout.PAGE_START);
        calPanel.add(btnPanel, BorderLayout.CENTER);
        getContentPane().add(calPanel);
    }

    public int apply (int ans, char opr, int nk)  {
        System.out.println("ans1: " + ans + " opr: " + opr + " nk: " +nk);
        switch (opr) {
            case '+': return ans += nk;
            case '-': return ans -= nk;
            case 'X': return ans *= nk;
            case '/': return ans /= nk;
        } return -1;
    }

    public boolean action(Event ce, Object co)  {
        key = ((String)co).charAt(0);
        System.out.println("key: " + key);
        if (key == 'C') {
            nk = 0;
            opr = ' ';
            ans = 0;
            key = ' ';
            calcTextField.setText("0");
        }
        else if((key >= '0') && (key <= '9')) {
            if(opr == ' ' && ans != 0) {
                nk = 0;
                ans = 0;
            }
            nk = 10 * nk + (key - '0');
            calcTextField.setText("" + nk);
        }
        else if (key == '=') {
            nk = apply (ans,opr,nk);
            calcTextField.setText("" + nk);
            opr = ' ';
            key = ' ';
            System.out.println("= opr: " + opr);
            System.out.println("= ans: " + nk);
        }
        else {
            if (opr == ' ') {
                ans = nk;
                calcTextField.setText("" + ans);
                opr = key;
                System.out.println("if ans: " + ans);
            }
            else {
                ans = apply (ans,opr,nk);
                calcTextField.setText("" + ans);
                opr = key;
                System.out.println("else ans: " + ans);
            }
            nk = 0;
        }
        return true;
    }

    public static void main(String[] s){
        new CalculatorA().setVisible(true);
    }
}
