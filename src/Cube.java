import com.raylib.Raylib.*;
import org.bytedeco.javacpp.Pointer;


public class Cube extends  Rectangle {
    private boolean accessible;
    private boolean isStart;
    private boolean isEnd;
    private Color color;

    public Cube(boolean isStart,boolean isEnd,Color color){
        this.isStart =isStart;
        this.isEnd =isEnd;
        this.color =color;

    }

    public  boolean isAccessible(){
        return this.accessible;
    }
    public  void setAccessible(boolean access){
        this.accessible=access;
    }

    public boolean isStart(){
        return this.isStart;
    }
    public void setStart(boolean start){
        this.isStart=start;

    }
    public boolean isEnd(){
        return this.isEnd;
    }
    public void setEnd(boolean end){
        this.isEnd=end;

    }
    public void setColor(Color color){
        this.color =color;
    }
    public Color getColor(){
         return this.color;
    }

}
