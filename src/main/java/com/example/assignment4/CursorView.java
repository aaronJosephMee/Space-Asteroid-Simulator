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
            mouseX = iModel.getMouseX() * canvasSize;
            mouseY = iModel.getMouseY() * canvasSize;
        }
        drawCursor(mouseX, mouseY);
    }

}
