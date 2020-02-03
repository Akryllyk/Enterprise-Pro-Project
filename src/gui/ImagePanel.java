/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author Aaron
 */
public class ImagePanel extends JPanel implements MouseInputListener, MouseWheelListener {

    public static final Dimension DEFAULTDIMENSION = calculateImageDimensions();
    private BufferedImage image;
    private boolean boxVisibility;
    private Dimension imageDimensions;
    private Point imagePosition;
    private Point mouseCoordinates;
    private int mouseClicked;
    private int angleDegrees;
    private Graphics2D graphicsHandler;
    private Rectangle box;
    private Point boxPosition;
    private Dimension boxDimensions;

    /**
     * Default constructor, generates an image panel without an image stored.
     * Used for if you need to generate a panel to display an image at a later
     * point.
     *
     * @throws java.io.IOException thrown to the GUI to prompt the user that the
     * file wasn't found.
     */
    public ImagePanel() throws IOException {
        this(null);
    }
    /**
     * Calculates "ideal" image dimensions based on user's display settings.
     * @return Returns "ideal" image dimensions
     */
    static public Dimension calculateImageDimensions(){
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/3,Toolkit.getDefaultToolkit().getScreenSize().height/3);
        return d;
    }

    /**
     * Takes a file location generating an ImagePanel with an Image already.
     * Default dimensions of 200x200 at origin of 0x0.
     *
     * @param fileLocation location of the image.
     * @throws java.io.IOException thrown to the GUI to prompt the user that the
     * file wasn't found.
     */
    public ImagePanel(BufferedImage image) throws IOException {
        this(image, DEFAULTDIMENSION.width, DEFAULTDIMENSION.height);
    }

    /**
     * Takes a file location along with width and height on an image you wish to
     * display. Generates an image panel containing an image of width and height
     * specified at a position of 0x0;
     *
     * @param fileLocation location of the image.
     * @param width width of the image in px.
     * @param height height of the image in px.
     * @throws java.io.IOException thrown to the GUI to prompt the user that the
     * file wasn't found.
     */
    public ImagePanel(BufferedImage image, int width, int height) throws IOException {
        this(image, width, height,5, 25, false);
    }

    /**
     * Take a file location along with width, height, xPosition and yPosition of
     * the image. Generates an image panel containing an image of width and
     * height specified at a specified position of x and y.
     *
     * @param fileLocation location of the image.
     * @param width width of the image in px.
     * @param height height of the image in px.
     * @param xPosition x position of the image in px. Acts as an offset.
     * @param yPosition y position of the image in px. Acts as an offset.
     * @throws java.io.IOException thrown to the GUI to prompt the user that the
     * file wasn't found.
     */
    public ImagePanel(BufferedImage image, int width, int height, int xPosition, int yPosition, boolean boxVisibility) throws IOException {
        this.boxVisibility = boxVisibility;
        setImage(image);
        setImagePosition(xPosition, yPosition);
        setImageDimensions(width, height);
        setBoxPosition(xPosition, yPosition);
        setBoxDimensions(width, height);
        angleDegrees = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        mouseClicked = 0;
    }

    /**
     * Allows setting an image to the image panel. Uses pre-existing dimensions
     * and position. Default dimensions and position are 200x200 at origin 0x0.
     *
     * @param fileLocation
     * @throws java.io.IOException thrown to the GUI to prompt the user that the
     * file wasn't found.
     */
    public void setImage(BufferedImage image) throws IOException {
        if (image != null) {
            this.image = image;
            repaint();
        }
    }

    public void setBoxVisiblity(boolean visibility) {
        boxVisibility = visibility;
        repaint();
    }

    /**
     * Sets the dimensions of the image in px.
     *
     * @param width desired width of the image.
     * @param height desired height of the image.
     */
    private void setImageDimensions(int width, int height) {
        imageDimensions = new Dimension(width, height);
    }

    /**
     * Used to set new coordinates for the image. These are an offset of x,y
     * from origin 0,0.
     *
     * @param x x offset from panel origin.
     * @param y y offset from panel origin.
     */
    private void setImagePosition(int x, int y) {
        imagePosition = new Point(x, y);
    }

    /**
     * Sets the dimensions for the box overlay.
     *
     * @param width width of the box.
     * @param height height of the box.
     */
    private void setBoxDimensions(int width, int height) {
        boxDimensions = new Dimension(width, height);
    }

    /**
     * Sets the position of the box offset from origin of panel 0,0
     *
     * @param x distance in px from origin of panel
     * @param y distance in px from origin of panel
     */
    private void setBoxPosition(int x, int y) {
        boxPosition = new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphicsHandler = (Graphics2D) g;
        graphicsHandler.drawImage(image, imagePosition.x, imagePosition.y, imageDimensions.width, imageDimensions.height, this);
        if (boxVisibility) {
            box = new Rectangle(boxPosition.x, boxPosition.y, boxDimensions.width, boxDimensions.height);
            graphicsHandler.rotate(Math.toRadians(angleDegrees), boxPosition.x + boxDimensions.width / 2, boxPosition.y + boxDimensions.height / 2);
            graphicsHandler.setColor(Color.yellow);
            graphicsHandler.draw(box);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        mouseClicked = e.getButton();
        mouseCoordinates = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        mouseClicked = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseClicked == MouseEvent.BUTTON1) {
            boxPosition.x += e.getX() - mouseCoordinates.x;
            boxPosition.y += e.getY() - mouseCoordinates.y;
            mouseCoordinates = new Point(e.getX(), e.getY());
        } else if (mouseClicked == MouseEvent.BUTTON3) {
            angleDegrees += (e.getY() - mouseCoordinates.y);
            mouseCoordinates.y = e.getY();
        }
        this.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        boxDimensions.height += e.getWheelRotation() * 2;
        boxPosition.y -= e.getWheelRotation();
        this.repaint();
    }

}
