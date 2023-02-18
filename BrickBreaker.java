
import javax.swing.*;

public class BrickBreaker {

    public static void main(String args[]){
        JFrame frame = new JFrame();
        Gameplay gameplay = new Gameplay();
        frame.setBounds(10, 10, 700, 600);
        frame.setResizable(false);
        frame.setTitle("Brick Breaker");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameplay);
    }
}
