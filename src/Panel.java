import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int cuerpo = 6;
    int puntos;
    int puntoX;
    int puntoY;
    char direccion = 'D';
    boolean running = false;
    Timer timer;
    Random random;

    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        jugar();
    }

    public void jugar(){
        generarPunto();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        dibujar(g);

    }

    public void dibujar(Graphics g){
        if(running) {
            g.setColor(Color.gray);
            for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }

            g.setColor(Color.yellow);
            g.fillOval(puntoX, puntoY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i< cuerpo; i++) {
                if(i==0) {
                    g.setColor(new Color(171, 122, 167));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(160, 99, 156));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 15));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Puntaje: " + puntos, (SCREEN_WIDTH - metrics.stringWidth("Puntaje: " + puntos))/2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void generarPunto(){
        puntoX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        puntoY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void mover(){
        for(int i = cuerpo; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direccion){
            case 'A':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'B':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'I':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'D':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkPunto(){
        if((x[0] == puntoX) && (y[0] == puntoY)){
            cuerpo++;
            puntos++;
            generarPunto();
        }
    }

    public void checkColision(){
        for(int i = cuerpo; i > 0; i--) {
            if((x[0] == x[i])&& (y[0] == y[i])){
                running = false;
            }
        }

        //Colisión con el borde izquierdo
        if(x[0] < 0){
            running = false;
        }

        //Colisión con el borde derecho
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }

        //Colisión con el borde inferior
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        //Colisión con el borde superior
        if(y[0] < 0){
            running = false;
        }

        if(running == false) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 15));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Puntaje: " + puntos, (SCREEN_WIDTH - metrics.stringWidth("Puntaje: " + puntos))/2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            mover();
            checkPunto();
            checkColision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direccion != 'D') {
                        direccion = 'I';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direccion != 'I') {
                        direccion = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direccion != 'B') {
                        direccion = 'A';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direccion != 'A') {
                        direccion = 'B';
                    }
                    break;
            }
        }
    }

}
