package by.unil2.itstep.testSring1.controllers.webentity;

public class ErrorMessage {
    String errorStr;

    public ErrorMessage(String inpString) {
        this.errorStr = inpString;
        }


    public String getErrorStr() {return errorStr; }
    public void setErrorStr(String errorStr) { this.errorStr = errorStr; }


}
