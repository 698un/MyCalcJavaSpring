package by.unil2.itstep.testSring1.controllers.webentity;

import by.unil2.itstep.testSring1.dao.model.PixelLine;

public class NewTask {

    private String sceneKey;
    private int frame;
    private int line;


    public void setSceneKey(String inpString){
        this.sceneKey = inpString;
    }
    public String getSceneKey() {     return sceneKey;  }

    public int getFrame() {     return frame;  }
    public void setFrame(int frame) {     this.frame = frame;  }

    public int getLine() {     return line; }
    public void setLine(int line) {     this.line = line;  }


    public NewTask(PixelLine pixLine) {
        this.frame = pixLine.getFrameNumber();
        this.line = pixLine.getLineNumber();
        }



}
