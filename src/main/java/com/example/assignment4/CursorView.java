package com.example.assignment4;

public class CursorView extends SpaceView implements Subscriber
{
    private double mouseX, mouseY;

    public CursorView(int canvasSize)
    {
        super(canvasSize);
        mouseX = 0;
        mouseY = 0;
    }



    private void drawCursor(double x, double y) {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.save();

        //TODO delete this

        // issue: either redrawing the view its not being centred, taking mouse coords and untransforming them
        // print the mouseX and mouseY after its transformed it should be (0,0)
        // it should be between 0.5 to -0.5 (these are home coords), all points should be in that range
        // ^^ thus, use home coords and normalized vals
        // the rotation of world will be applied to all asteroid
        // for each asteroid itself, have separate gc.save(), gc.store(), there you can do translate and scale and
        // rotate there

        // need instance var for scale factor (width of asteroid) multiply that by the canvas to get the size of
        // asteroid
        // random radius for asteroid, between 0.001 and 0.002 (its scaled essentially)

        // for home coords:
        // he gets random number of vertices, for that num of vertices, goes around a circle, generate random
        // distance from that circle, radius value will be quite small, bc you are going around the circle it should
        // be home coords (all of x and y points will be between 0.5 and -0.5, but they will likely be smaller, like
        // 0.05 to -0.05)
        // ^^ translate using the home coords, math.random() - 0.5   that is like our tX and tY
        // ^^ trying to get centre of asteroid at (0,0); the asteroid needs to be shifted (maybe), just so long as I
        // am using home coords

        // when you create asteroid, they have their coords
        // then draw it using translate, scale, rotate
        // your mouse location is relative to the translation that I did
        gc.translate(-x, -y);
        gc.scale(2, 2);

        drawOuterSpace();
        gc.restore();
    }

    public void receiveNotification(ChannelName channelName)
    {
        if(channelName == ChannelName.CREATE_STAR)
        {
            this.stars = spaceModel.getStars();
        }
        else if(channelName == ChannelName.CREATE_ASTEROID)
        {
            this.asteroids = spaceModel.getAsteroidList();
        }
        else if (channelName == ChannelName.MOUSE_MOVED)
        {
            //TODO delete print when done
            //System.out.println("Cursor coordinates: " + iModel.getMouseX() + " " + iModel.getMouseY());
            mouseX = iModel.getMouseX() * canvasSize;
            mouseY = iModel.getMouseY() * canvasSize;
        }
        drawCursor(mouseX, mouseY);
    }

}
