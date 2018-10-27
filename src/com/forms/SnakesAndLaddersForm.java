package com.forms;

import com.snakesandladders.GameConductor;
import com.snakesandladders.Player;
import com.sun.jmx.snmp.SnmpDataTypeEnums;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by shash on 10/16/2018.
 */
public class SnakesAndLaddersForm {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel playerOnePanel;
    private JPanel playerTwoPanel;
    private JPanel imagePanel;
    private JButton playerOneButton;
    private JButton playerTwoButton;
    private JLabel playerOneLabel;
    private JLabel playerTwoLabel;
    private JLabel imageLabel;
    private JButton createPuzzleButton;

    private Player player1;
    private Player player2;

    private GameConductor gc = new GameConductor();
    Icon originalIcon;

    public SnakesAndLaddersForm() {

        player1 = new Player();
        player2 = new Player();

        player1.setOpponent(player2);
        player2.setOpponent(player1);

        int snakePositions[][] = gc.getSnakePositions();
        int ladderPositions[][] = gc.getLadderPositions();


        BufferedImage img = joinBufferedImage(snakePositions, ladderPositions);
        imageLabel.setIcon(new ImageIcon(img));
        originalIcon= imageLabel.getIcon();

        playerOneButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                int tempIndice = gc.getNewIndice();
                player1.setCurrentNumber(tempIndice);
                playerOneLabel.setText(tempIndice + "");
                player1 = gc.playGame(player1);

                if (!player1.isActive()) {
                    playerTwoButton.setEnabled(true);
                    playerOneButton.setEnabled(false);
                }

                if (player1.isWinner()) {
                    playerTwoButton.setEnabled(false);
                    playerOneButton.setEnabled(false);
                }

                showButtonPositions(player1, player2, imageLabel, originalIcon);
            }
        });

        playerTwoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Icon originalIcon = imageLabel.getIcon();
                int tempIndice = gc.getNewIndice();
                player2.setCurrentNumber(tempIndice);
                playerTwoLabel.setText(tempIndice + "");
                player2 = gc.playGame(player2);


                if (!player2.isActive()) {
                    playerTwoButton.setEnabled(false);
                    playerOneButton.setEnabled(true);
                }

                if (player2.isWinner()) {
                    playerTwoButton.setEnabled(false);
                    playerOneButton.setEnabled(false);
                }

                showButtonPositions(player1, player2, imageLabel, originalIcon);

            }
        });

        createPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gc.setSnakePositions();
                gc.setLadderPositions();

                int snakePositions[][] = gc.getSnakePositions();
                int ladderPositions[][] = gc.getLadderPositions();

                BufferedImage img = joinBufferedImage(snakePositions, ladderPositions);
                imageLabel.setIcon(new ImageIcon(img));
                originalIcon = imageLabel.getIcon();

                playerOneButton.setEnabled(true);
                playerTwoButton.setEnabled(true);

                playerOneLabel.setText("P1");
                playerTwoLabel.setText("P2");


            }
        });
    }

    private void showButtonPositions(Player p1, Player p2, JLabel imageLabel, Icon icon) {

        int player1Row = getButtonRowPosition(p1);
        int player1Column = getButtonColumnPosition(p1);

        int player2Row = getButtonRowPosition(p2);
        int player2Column = getButtonColumnPosition(p2);

        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0, 0);


        g.setColor(Color.YELLOW);
        g.fillOval(player1Column, player1Row, 50, 50);

        g.setColor(Color.RED);
        g.fillOval(player2Column + 5, player2Row + 5, 50, 50);

        imageLabel.setIcon(null);
        imageLabel.setIcon(new ImageIcon(bi));

        //paintComponent('');
    }

    private int getButtonRowPosition(Player p) {
        int row = (int) Math.ceil((p.getCurrentPosition() - 1) / 6);
        int rowPosition = (int) (550 + (row * (-100)));

        if (rowPosition < 0) {
            rowPosition = 50;
        }

        return rowPosition;
    }

    private int getButtonColumnPosition(Player p) {

        int row = (int) Math.ceil((p.getCurrentPosition() - 1) / 6);
        int columnPosition;
        if (row % 2 == 1) {
            columnPosition = (int) (550 + (((p.getCurrentPosition() - 1) % 6) * (-100)));
        } else {
            columnPosition = (int) (50 + (((p.getCurrentPosition() - 1) % 6) * (100)));
        }
        //joinBufferedImage();
        return columnPosition;
    }

    public static void main(String args[]) {
        JFrame mainFrame = new JFrame("Snakes & Ladders");
        mainFrame.setContentPane(new SnakesAndLaddersForm().mainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public BufferedImage joinBufferedImage(int snakePositions[][], int ladderPositions[][]) {

        ClassLoader classLoader = getClass().getClassLoader();

        BufferedImage grid = null;

        BufferedImage snake;
        BufferedImage rotatedSnake = null;

        BufferedImage ladder;
        BufferedImage rotatedLadder = null;


        String pathString = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/images/snakes_ladders_1.JPG";
        pathString = pathString.replace("\\", "/");

        try {
            grid = ImageIO.read(new File(pathString));
        } catch (IOException e) {
            e.printStackTrace();
        }


        pathString = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/images/snake.png";
        pathString = pathString.replace("\\", "/");
        //System.out.println(pathString);
        try {
            snake = ImageIO.read(new File(pathString));

            for (int i = 0; i < 3; i++) {
                AffineTransform at = new AffineTransform();

                double d[] = getAngleScale(snakePositions[i][0], snakePositions[i][1]);
                System.out.println("snake");
                System.out.println(snakePositions[i][0] + "," + snakePositions[i][1]);
                //System.out.println(d);
                /**
                 * rotating the image
                 */
                rotatedSnake = rotateImage(snake, (int) d[0]);

                /**
                 * write the rotated image as a new image and assign it to new variable rotatedSnake
                 */
                String rotatedImagePathString = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/images/snake_rotated.png";
                rotatedImagePathString = rotatedImagePathString.replace("\\", "/");
                ImageIO.write(rotatedSnake, "png", new File(rotatedImagePathString));
                rotatedSnake = ImageIO.read(new File(rotatedImagePathString));


                /**
                 * scale the rotated image as wish
                 */
                at.scale(d[1], d[1]);
                AffineTransformOp op;
                op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                rotatedSnake = op.filter(rotatedSnake, null);

                /**
                 * draw it on grid image
                 */
                Graphics gg = grid.getGraphics();
                gg.drawImage(rotatedSnake, (int) d[2], (int) d[3], null);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        pathString = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/images/ladder.png";
        pathString = pathString.replace("\\", "/");
        //System.out.println(pathString);
        try {
            ladder = ImageIO.read(new File(pathString));

            for (int i = 0; i < 3; i++) {
                AffineTransform at = new AffineTransform();

                double d[] = getAngleScale(ladderPositions[i][1], ladderPositions[i][0]);
                System.out.println("Ladder");
                System.out.println(ladderPositions[i][1] + "," + ladderPositions[i][0]);
                //System.out.println(d);

                /**
                 * rotating the image
                 */
                rotatedLadder = rotateImage(ladder, (int) d[0]);

                /**
                 * write the rotated image as a new image and assign it to new variable rotatedLadder
                 */
                String rotatedImagePathString = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/images/ladder_rotated.png";
                rotatedImagePathString = rotatedImagePathString.replace("\\", "/");
                ImageIO.write(rotatedLadder, "png", new File(rotatedImagePathString));
                rotatedLadder = ImageIO.read(new File(rotatedImagePathString));


                /**
                 * scale the rotated image as wish
                 */
                at.scale(d[1], d[1]);
                AffineTransformOp op;
                op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                rotatedLadder = op.filter(rotatedLadder, null);

                /**
                 * draw it on grid image
                 */
                Graphics gg = grid.getGraphics();
                gg.drawImage(rotatedLadder, (int) d[2], (int) d[3], null);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return grid;

    }

    /**
     * get the angle and  distance between two grid / box locations
     *
     * @param boxNumber1 position o
     * @param boxNumber2
     * @return
     */
    public double[] getAngleScale(int boxNumber1, int boxNumber2) {
        int row1, row2 = 0;
        int column1, column2 = 0;

        row1 = 7 - (int) (Math.ceil((double) boxNumber1 / 6));
        if (row1 % 2 == 1) {
            column1 = (7 - row1) * 6 - boxNumber1 + 1;
        } else {
            column1 = 6 - (7 - row1) * 6 + boxNumber1;
        }

        row2 = 7 - (int) (Math.ceil((double) boxNumber2 / 6));
        if (row2 % 2 == 1) {
            column2 = (7 - row2) * 6 - boxNumber2 + 1;
        } else {
            column2 = 6 - (7 - row2) * 6 + boxNumber2;
        }

        double tanTheta = 0;
        double theta = 0;


        int xDifference = column2 - column1;
        int yDifference = row2 - row1;

        if ((row2 - row1) != 0) {
            tanTheta = -((double) xDifference) / ((double) yDifference);
            System.out.println("tan theta" + tanTheta);
            theta = Math.toDegrees(Math.atan(tanTheta));
        } else {
            theta = 90;
        }


        double hypotenus = Math.sqrt((xDifference * xDifference) + (yDifference * yDifference));
        double scaleFactor = hypotenus * (0.3 / (5 * Math.sqrt(2)));


        //System.out.println(row1 + ",  " + column1);
        //System.out.println(row2 + ",  " + column2);

        int startXPoint = column1 * 100 - 50;
        int startYPoint = row1 * 100 - 50;

        if (theta > 0) {
            startXPoint = column2 * 100 - 50;
        }

        double d[] = new double[4];
        d[0] = theta;
        d[1] = scaleFactor;
        d[2] = startXPoint;
        d[3] = startYPoint;

      /*  System.out.println(xDifference);
        System.out.println(yDifference);

        System.out.println(hypotenus);
        System.out.println(d[0]);
        System.out.println(d[1]);*/
        return d;

    }

    /**
     * rotate the buffered image by certain angle
     *
     * @param originalImage
     * @param degree
     * @return
     */
    public BufferedImage rotateImage(BufferedImage originalImage, double degree) {
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        double toRad = Math.toRadians(degree);
        int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
        int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

        BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = rotatedImage.createGraphics();
        int alpha = 1000; // 50% transparent
        g.translate(wPrime / 2, hPrime / 2);
        g.rotate(toRad);
        g.translate(-w / 2, -h / 2);
        g.drawImage(originalImage, 0, 0, null);

        Color myColour = new Color(0, 0, 0, 50);
        g.setColor(myColour);
        g.fillRect(0, 0, 10, 10);  // fill entire area

        g.dispose();  // release used resources before g is garbage-collected
        return rotatedImage;
    }


}
