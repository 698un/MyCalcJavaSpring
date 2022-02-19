package by.unil2.itstep.testSring1.controllers.webentity;

import by.unil2.itstep.testSring1.utilits.CalcOptions;

public class ServerStatus {

    private int clientCount;
    private int imgWidth;
    private int imgHeight;
    private int imgAntialiasing;
    private int fps;

    public ServerStatus() {  }

    public int getClientCount() {    return clientCount;  }
    public void setClientCount(int clientCount) {     this.clientCount = clientCount;   }

    public int getImgWidth() {        return imgWidth;    }
    public void setImgWidth(int imgWidth) {    this.imgWidth = imgWidth;    }

    public int getImgHeight() {      return imgHeight;   }
    public void setImgHeight(int imgHeight) {    this.imgHeight = imgHeight;   }

    public int getImgAntialiasing() {    return imgAntialiasing;  }
    public void setImgAntialiasing(int imgAntialiasing) {   this.imgAntialiasing = imgAntialiasing;  }

    public int getFps() {    return fps;  }
    public void setFps(int fps) {     this.fps = fps;   }




}
