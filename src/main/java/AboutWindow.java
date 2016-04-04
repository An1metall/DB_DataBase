import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutWindow extends JFrame{

    public AboutWindow() throws HeadlessException {
        setSize(400, 60);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setAlwaysOnTop(true);
        setUndecorated(true);

        JPanel jp = new JPanel(new FlowLayout());
        add(jp);
        JLabel label1 = new JLabel("Спасибо порталу GeekBrains.ru за обучение!");
        jp.add(label1, BorderLayout.CENTER);
        JLabel label2 = new JLabel("Спасибо порталу randus.ru за генератор случайных данных!");
        jp.add(label2, BorderLayout.CENTER);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
